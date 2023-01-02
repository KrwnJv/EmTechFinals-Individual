package com.example.biopin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biopin.GeoLocationAPI.LocationApi
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class ApiViewModel: ViewModel() {

    private val _latLng = MutableLiveData<LatLng>()
    val latLng: LiveData<LatLng> = _latLng

    fun getLocationApi(){
        viewModelScope.launch {
            try {
               val  listCoordinates = LocationApi.retrofitService.getLatLng()
                _latLng.value = LatLng(listCoordinates.x,listCoordinates.y)
            } catch (e: Exception){
            }
        }
    }
}