package com.binar.challengechapterempat

import UserManager
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.delete_dialog.view.*
import kotlinx.android.synthetic.main.edit_dialog.view.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val getnotes = arguments?.getParcelable<Notes>("detailnote") as Notes
        userManager = UserManager(requireContext())
        val current = LocalDateTime.now()
        val format = current.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))


        if (getnotes != null) {
            view.text1n.text = getnotes.judul
            view.text2n.text = getnotes.isi

        }

        view.btnshare.setOnClickListener{
            val intent= Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, getnotes.isi)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }

        view.btndelete.setOnClickListener{
            db = NotesDB.getInstance(it.context)
            val custom = LayoutInflater.from(it.context).inflate(R.layout.delete_dialog, null)
            val a = androidx.appcompat.app.AlertDialog.Builder(it.context)
                .setView(custom)
                .create()

            custom.btnhapustidak.setOnClickListener {
                a.dismiss()
            }

            custom.btnhapusya.setOnClickListener {



                GlobalScope.async {
                    db?.NotesDao()?.deleteNoteCustom(getnotes?.id.toString())
                    view.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                }
                a.dismiss()

            }
            a.show()
        }

        view.btnedita.setOnClickListener{
            db = NotesDB.getInstance(it.context)
            val custom = LayoutInflater.from(it.context).inflate(R.layout.edit_dialog, null)
            val a = androidx.appcompat.app.AlertDialog.Builder(it.context)
                .setView(custom)
                .create()

            var judul = getnotes?.judul
            var isi = getnotes?.isi
            var id = getnotes?.id
            var email = getnotes?.email


            custom.editjudul.setText(judul)
            custom.editcatatan.setText(isi)

            custom.editjudul.setSelection(custom.editjudul.length())
            custom.editcatatan.setSelection(custom.editcatatan.length())

            custom.btnedit.setOnClickListener {

                judul = custom.editjudul.text.toString()
                isi = custom.editcatatan.text.toString()

                GlobalScope.async {
                     db?.NotesDao()?.updateNotes(Notes(id,judul,isi,email, format.toString()))


                }
                a.dismiss()
                view.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }
            a.show()

            }


        return view


    }

}