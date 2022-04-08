package com.binar.challengechapterempat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.binar.challengechapterempat.R
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var email : String
    lateinit var get : SharedPreferences
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var db : NotesDB?=null
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
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val btnregis = view.findViewById<Button>(R.id.btnregis)
        get = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)
        db = NotesDB.getInstance(requireActivity())
        btnregis.setOnClickListener {
            val username = regisusername.text.toString()
            email = regisemail.text.toString()
            val regis_pass = regispassword.text.toString()
            val confirmpass = confirmpassword.text.toString()


            if (regisusername.text.isNotEmpty() && regisemail.text.isNotEmpty()
                && regispassword.text.isNotEmpty()
                && confirmpassword.text.isNotEmpty() ){

                if (regis_pass == confirmpass){

                    val edit = get.edit()
                    edit.putString("username", username)
                    edit.apply()

                    GlobalScope.async {
                        val user = db?.NotesDao()?.getUserRegis(email)
                        if (user != null) {
                            requireActivity().runOnUiThread {
                                Toast.makeText(
                                    requireContext(),
                                    "User dengan email ${user.email} sudah terdaftar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }else{
                            val regis = db?.NotesDao()?.registerUser(User(email, username, regis_pass))


                            requireActivity().runOnUiThread{
                                if (regis !=0.toLong()){
                                    Toast.makeText(requireContext(), "Register Berhasil", Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(requireContext(), " RegisterGagal", Toast.LENGTH_LONG).show()
                                }
                            }
                            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                        }

                    }



                }else{
                    val text = "Konfirmasi Password Tidak Sesuai"
                    val toast = Toast.makeText(requireActivity()?.getApplicationContext(), text, Toast.LENGTH_LONG)
                    val text1 = toast.getView()?.findViewById(android.R.id.message) as TextView
                    val toastView: View? = toast.getView()
                    toastView?.setBackgroundColor(Color.TRANSPARENT)
                    text1.setTextColor(Color.RED);
                    text1.setTextSize(15F)
                    toast.show()
                    toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 1420)
                }



            }else {
                val text = "Harap isi semua data"
                val toast = Toast.makeText(
                    requireActivity()?.getApplicationContext(),
                    text,
                    Toast.LENGTH_LONG
                )
                val text1 = toast.getView()?.findViewById(android.R.id.message) as TextView
                val toastView: View? = toast.getView()
                toastView?.setBackgroundColor(Color.TRANSPARENT)
                text1.setTextColor(Color.RED);
                text1.setTextSize(15F)
                toast.show()
                toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 1420)

            }

        }



        return view
    }





    }




