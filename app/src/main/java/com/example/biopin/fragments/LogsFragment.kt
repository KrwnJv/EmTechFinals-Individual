package com.example.biopin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.biopin.MainApplication
import com.example.biopin.R
import com.example.biopin.adapter.LogsAdapter
import com.example.biopin.database.models.Logs
import com.example.biopin.databinding.FragmentLogsBinding
import com.example.biopin.viewmodel.LogsViewModel
import com.example.biopin.viewmodel.LogsViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.nio.file.Files.delete

class LogsFragment : Fragment() {

    private val viewModel: LogsViewModel by activityViewModels {
        LogsViewModelFactory(
            (activity?.application as MainApplication).database
                .logsDao()
        )
    }

    private var _binding: FragmentLogsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setTitle("Logs")
        _binding = FragmentLogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = LogsAdapter(
            {
                logs: Logs -> confirmationDialog(logs)
            },
            {
                val action = LogsFragmentDirections.actionLogsFragmentToUpdateFragment(it.User, it.Remarks, it.id)
                findNavController().navigate(action)
            }
        )
        recyclerView.adapter = adapter


        lifecycle.coroutineScope.launch{
            viewModel.getAllLogs().collect{ listLogs ->
                adapter.submitList(listLogs)
            }
        }
    }

    private fun confirmationDialog(logs: Logs) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete this log item?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
//          Delete log
            .setPositiveButton("Yes") { _, _ -> deleteLog(logs) }
            .show()
    }

    private fun deleteLog(logs: Logs) {
        viewModel.deleteLog(logs)
    }
}