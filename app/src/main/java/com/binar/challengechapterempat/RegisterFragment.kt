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
import androidx.navigation.findNavController
import com.binar.challengechapterempat.R
import kotlinx.android.synthetic.main.fragment_register.*

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
    lateinit var get : SharedPreferences
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        btnregis.setOnClickListener {
            val username = regisusername.text.toString()
            val regis_email = regisemail.text.toString()
            val regis_pass = regispassword.text.toString()
            val confirmpass = confirmpassword.text.toString()

            if (regisusername.text.isNotEmpty() && regisemail.text.isNotEmpty()
                && regispassword.text.isNotEmpty()
                && confirmpassword.text.isNotEmpty() ){

                if (regis_pass == confirmpass){
                    val edit = get.edit()
                    edit.putString("username", username)
                    edit.putString("regisemail", regis_email)
                    edit.putString("regispassword", regis_pass)

                    edit.apply()
                    view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}