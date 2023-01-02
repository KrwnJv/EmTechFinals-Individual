package com.example.biopin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.biopin.MainApplication
import com.example.biopin.R
import com.example.biopin.database.models.Logs
import com.example.biopin.databinding.FragmentUpdateBinding
import com.example.biopin.viewmodel.LogsViewModel
import com.example.biopin.viewmodel.LogsViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class UpdateFragment : Fragment() {

    private val viewModel: LogsViewModel by activityViewModels {
        LogsViewModelFactory(
            (activity?.application as MainApplication).database
                .logsDao()
        )
    }

    private val navigationArgs: UpdateFragmentArgs by navArgs()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idArgs = navigationArgs.id

        lifecycle.coroutineScope.launch{
            getLog(idArgs).collect{
                binding.apply {
                    userUpdate.setText(it.User)
                    remarksUpdate.setText(it.Remarks)
                    coordinatesPlaceholder.setText(it.Coordinates)
                }
            }
        }

        binding.updateConfirmButton.setOnClickListener{
            updateLog(idArgs)
        }
        binding.updateCancelButton.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun getLog(id: Int): Flow<Logs> = viewModel.getLog(id)

    private fun updateLog(id: Int){
        viewModel.updateLog(
            id = id,
            User = binding.userUpdate.text.toString(),
            Remarks = binding.remarksUpdate.text.toString(),
            Coordinates = binding.coordinatesPlaceholder.text.toString()
        )
        findNavController().navigateUp()
    }

}