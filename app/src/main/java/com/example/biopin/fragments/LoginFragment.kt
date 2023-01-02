package com.example.biopin.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.biopin.MainActivity
import com.example.biopin.MainApplication
import com.example.biopin.R
import com.example.biopin.databinding.FragmentLoginBinding
import com.example.biopin.viewmodel.AuthViewModel
import com.example.biopin.viewmodel.AuthViewModelFactory

class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            (activity?.application as MainApplication).database
                .userDao()
        )
    }

    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
        binding.loginButton.setOnClickListener {
            loginUser()
            Log.d("LoginFragment", "Login Success")
        }
    }

    private fun entryValidationLogin(): Boolean{
        return viewModel.entryValidationLogin(
            username = binding.username.text.toString(),
            password = binding.password.text.toString()
        )
    }

    private fun userValidation(): Boolean {
        return viewModel.userValidation(
            username = binding.username.text.toString(),
            password = binding.password.text.toString()
        )
    }

    private fun loginUser() {
        if (entryValidationLogin())
            if (userValidation()) {
                startActivity(Intent(activity, MainActivity::class.java))

            } else {
                binding.username.error = null
                binding.password.error = getString(R.string.error_password)
            }
        else {
            var username = binding.username.text.toString()
            if (username.isEmpty()) {
                binding.username.error = getString(R.string.missing_entries)
                binding.password.error = null
            } else {
                binding.password.error = getString(R.string.missing_entries)
                binding.username.error = null
            }
        }
    }
}