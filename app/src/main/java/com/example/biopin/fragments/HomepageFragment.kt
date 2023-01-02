package com.example.biopin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.biopin.MainApplication
import com.example.biopin.R
import com.example.biopin.databinding.FragmentHomepageBinding
import com.example.biopin.viewmodel.LogsViewModel
import com.example.biopin.viewmodel.LogsViewModelFactory

class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle("Home")
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.LogsButton.setOnClickListener{
            val action = HomepageFragmentDirections.actionHomepageFragmentToLogsFragment()
            findNavController().navigate(action)
        }
        binding.TrackingButton.setOnClickListener {
            val action = HomepageFragmentDirections.actionHomepageFragmentToMapsFragment()
            findNavController().navigate(action)
        }
    }

}