package com.binar.challengechapterempat

import UserManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.delete_dialog.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_logout_dialog.*
import kotlinx.android.synthetic.main.fragment_logout_dialog.view.*
import kotlinx.android.synthetic.main.fragment_logout_dialog.view.btnlogouttidak
import kotlinx.android.synthetic.main.fragment_logout_dialog.view.btnlogoutya
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.input_dialog.*
import kotlinx.android.synthetic.main.input_dialog.view.*
import kotlinx.android.synthetic.main.logout_dialog.view.*
import kotlinx.coroutines.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var email: String
    private lateinit var user: User
    lateinit var adapterNote : AdapterNotes
    lateinit var userManager: UserManager
    lateinit var home: SharedPreferences
    var db: NotesDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        userManager = UserManager(requireContext())
        userManager.userEmail.asLiveData().observe(requireActivity()){
            email = it
            GlobalScope.launch {
                val listdata = db?.NotesDao()?.getNoteAcc(email)

                requireActivity().runOnUiThread {
                    listdata.let { it ->
                        if (listdata?.size == 0) {
                            checkdata.text = "Belum ada catatan"
                        }
                        adapterNote = AdapterNotes(){
                            val bund = Bundle()
                            bund.putParcelable("detailnote", it)
                            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bund)
                        }
                        adapterNote.setData(it!!)

                        list?.adapter = adapterNote

                    }
                }
            }



        }
        AndroidThreeTen.init(requireContext())
        val current = LocalDateTime.now()
        val format = current.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))

        db = NotesDB.getInstance(requireContext())
        view.list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        email =""
        GlobalScope.launch {


//            GlobalScope.async {
//                val user = db?.NotesDao()?.getUserRegis(email)
//
//
//            }


        }

        view.profile.setOnClickListener{
            view.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }



        view.add.setOnClickListener {
            val custom = LayoutInflater.from(requireContext()).inflate(R.layout.input_dialog, null)
            val a = AlertDialog.Builder(requireContext())
                .setView(custom)
                .create()

            custom.btninput.setOnClickListener {

                GlobalScope.async {

                    val judul = custom.judul.text.toString()
                    val isi = custom.catatan.text.toString()
//                    val user = db?.NotesDao()?.getUserRegis(email)
                    val hasil = db?.NotesDao()?.insertNote(Notes(null, judul, isi, email, format.toString()))

                    requireActivity().runOnUiThread {
                        if (hasil != 0.toLong()) {
                            Toast.makeText(requireContext(), "Berhasil", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                view.findNavController().navigate(R.id.homeFragment)

                a.dismiss()
            }

            a.show()


        }



        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */

        const val PREF_USER = "user_preference"
        const val EMAIL = "email"

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}