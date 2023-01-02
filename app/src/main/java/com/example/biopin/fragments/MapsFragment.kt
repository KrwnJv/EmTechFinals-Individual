package com.example.biopin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.biopin.MainApplication
import com.example.biopin.R
import com.example.biopin.viewmodel.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: LogsViewModel by activityViewModels {
        LogsViewModelFactory(
            (activity?.application as MainApplication).database
                .logsDao()
        )
    }

    private val viewModelAPI: ApiViewModel by activityViewModels()

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.setTitle("Track")
        viewModelAPI.getLocationApi()
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.Maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.mMap = googleMap

        viewModelAPI.latLng.observe(this){
            googleMap.addMarker(MarkerOptions().position(it))
            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            googleMap.uiSettings.isMapToolbarEnabled = true
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 20f))
            Log.d("test", "$it")

//          Create new log
            viewModel.newLogEntry(
                User = "Logged by user",
                Remarks = "<add remarks>",
                Coordinates = it.toString()
            )
        }
    }
}