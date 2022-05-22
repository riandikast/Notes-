package com.binar.challengechapterempat

import UserManager
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    lateinit var userManager : UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        userManager = UserManager(requireContext())

        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.username.setText("Welcome $it")
        }
        userManager.userEmail.asLiveData().observe(requireActivity()){
            view.email.setText("Email : $it")
        }
        view.btnlogout.setOnClickListener {
            GlobalScope.launch {
                userManager.deleteDataLogin()
            }
            view.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)

        }
        return view

    }

}