package com.example.biopin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.biopin.MainApplication
import com.example.biopin.R
import com.example.biopin.databinding.RegisterFragmentBinding
import com.example.biopin.viewmodel.AuthViewModel
import com.example.biopin.viewmodel.AuthViewModelFactory

class RegisterFragment: Fragment(){

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            (activity?.application as MainApplication).database
                .userDao()
        )
    }

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener{
            insertUser()
            Log.d("RegisterFragment", "Register Button")
        }

        binding.cancelButton.setOnClickListener{
            findNavController().navigateUp()
            Log.d("RegisterFragment", "Cancel Button")
        }
    }

    private fun entryValidationRegister(): Boolean{
        return viewModel.entryValidationRegister(
            username = binding.username.text.toString(),
            password = binding.password.text.toString(),
            confirmPassword = binding.confirmPassword.text.toString(),
            firstName = binding.firstname.text.toString(),
            lastName = binding.lastname.text.toString()
        )
    }

    private fun passwordSimilarity(): Boolean {
        return viewModel.passwordSimilarity(
            password = binding.password.text.toString(),
            confirmPassword = binding.confirmPassword.text.toString()
        )
    }

    private fun insertUser(){
        if (entryValidationRegister())
            if (passwordSimilarity()){
                viewModel.newUserEntry(
                    username = binding.username.text.toString(),
                    password = binding.password.text.toString(),
                    firstName = binding.firstname.text.toString(),
                    lastName = binding.lastname.text.toString()
                )
                binding.confirmPassword.error = null
                Toast.makeText(requireActivity(), "Added ${binding.username.text}", Toast.LENGTH_LONG).show()
                view?.findNavController()?.navigateUp()
            } else {
                binding.confirmPassword.error = "Password does not match!"
                binding.password.error= null
                binding.username.error = null
            }
        else {
            var username = binding.username.text.toString()
            var password = binding.password.text.toString()
            var firstName = binding.firstname.text.toString()
            var lastName = binding.lastname.text.toString()

            if(firstName.isEmpty()){
                binding.firstname.error = getString(R.string.missing_entries)
                binding.lastname.error = null
                binding.username.error = null
                binding.password.error= null
                binding.confirmPassword.error = null
            } else if (lastName.isEmpty()){
                binding.firstname.error = null
                binding.lastname.error = getString(R.string.missing_entries)
                binding.username.error = null
                binding.password.error= null
                binding.confirmPassword.error = null
            } else if(username.isEmpty()) {
                binding.firstname.error = null
                binding.lastname.error = null
                binding.username.error = getString(R.string.missing_entries)
                binding.password.error= null
                binding.confirmPassword.error = null
            } else if (password.isEmpty()) {
                binding.firstname.error = null
                binding.lastname.error = null
                binding.username.error = null
                binding.password.error= getString(R.string.missing_entries)
                binding.confirmPassword.error = null
            } else {
                binding.firstname.error = null
                binding.lastname.error = null
                binding.username.error = null
                binding.password.error= null
                binding.confirmPassword.error = getString(R.string.missing_entries)
            }
        }
    }
}