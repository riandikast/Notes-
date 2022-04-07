package com.binar.challengechapterempat

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.binar.challengechapterempat.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    lateinit var send : SharedPreferences
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
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val daftar = view.findViewById<TextView>(R.id.daftar2)
        val login = view.findViewById<Button>(R.id.btnlogin)

        send = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE)



        daftar.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        login.setOnClickListener {


            if (loginemail.text.isNotEmpty() && loginpassword.text.isNotEmpty()){

                val login_email = loginemail.text.toString()
                val login_password = loginpassword.text.toString()


                val email = send.getString("regisemail", "")
                val password = send.getString("regispassword", "")

                if (login_email == email && login_password == password){
                    val loginstate = "true"
                    val sf = send.edit()
                    sf.putString("loginemail", login_email)
                    sf.putString("loginpassword", login_password)
                    sf.putString("login_state", loginstate)
                    sf.apply()
                    view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                }else{
                    val text = "Email atau password salah!"
                    val toast = Toast.makeText(requireActivity()?.getApplicationContext(), text, Toast.LENGTH_LONG)
                    val text1 = toast.getView()?.findViewById(android.R.id.message) as TextView
                    val toastView: View? = toast.getView()
                    toastView?.setBackgroundColor(Color.TRANSPARENT)
                    text1.setTextColor(Color.RED);
                    text1.setTextSize(15F)
                    toast.show()
                    toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 960)

                }
            }else{
                val text = "Harap isi semua data"
                val toast = Toast.makeText(requireActivity()?.getApplicationContext(), text, Toast.LENGTH_LONG)
                val text1 = toast.getView()?.findViewById(android.R.id.message) as TextView
                val toastView: View? = toast.getView()
                toastView?.setBackgroundColor(Color.TRANSPARENT)
                text1.setTextColor(Color.RED);
                text1.setTextSize(15F)
                toast.show()
                toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 960)
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}