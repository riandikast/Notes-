package com.binar.challengechapterempat

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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.delete_dialog.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_logout_dialog.*
import kotlinx.android.synthetic.main.fragment_logout_dialog.view.*
import kotlinx.android.synthetic.main.fragment_logout_dialog.view.btnlogouttidak
import kotlinx.android.synthetic.main.fragment_logout_dialog.view.btnlogoutya
import kotlinx.android.synthetic.main.input_dialog.*
import kotlinx.android.synthetic.main.input_dialog.view.*
import kotlinx.android.synthetic.main.logout_dialog.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
        home = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)

//        val username = home.getString("username", "")

        var email = home.getString("regisemail", "").toString()

        db = NotesDB.getInstance(requireContext())
        view.list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        GlobalScope.launch {

//            val user = db?.NotesDao()?.getUserRegis(email)
            GlobalScope.async {
                val user = db?.NotesDao()?.getUserRegis(email)
                val username = user?.nama
                view.welcome.text = "Welcome " + username
                Log.d("uuuuuuu", user.toString())
            }

            val listdata = db?.NotesDao()?.getNoteAcc(email)
            Log.d ("www", listdata.toString())
            requireActivity().runOnUiThread {
                listdata.let {
                    if (listdata?.size == 0) {
                        checkdata.text = "Belum ada catatan"
                    }

                    val adapt = AdapterNotes(it!!)
                    list?.adapter = adapt

                }
            }
        }

        view.logout.setOnClickListener {
            val custom = LayoutInflater.from(requireContext()).inflate(R.layout.logout_dialog, null)
            val a = AlertDialog.Builder(requireContext())
                .setView(custom)
                .create()
            custom.btnlogouttidak.setOnClickListener {

                a.dismiss()
            }

            custom.btnlogoutya.setOnClickListener {
                val logout = home.edit()

                for (key in home.all.keys) {
                    if (key.startsWith("login_state")) {
                        logout.remove(key)
                    }
                }
                logout.commit()

                a.dismiss()
                view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
            custom.deleteacc.setOnClickListener {
                val logout =  home.edit()
                logout.clear()
                logout.apply()
                for (key in home.all.keys) {
                    if (key.startsWith("login_state")) {
                        logout.remove(key)
                    }
                }
                logout.commit()

                GlobalScope.async {
                    val user = db?.NotesDao()?.getUserRegis(email)
                    if (user != null) {
                        db?.NotesDao()?.deleteacc(user)
                    }
                }

                a.dismiss()
                view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
            a.show()
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
                    val user = db?.NotesDao()?.getUserRegis(email)
                    val hasil = db?.NotesDao()?.insertNote(Notes(null, judul, isi, email))

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