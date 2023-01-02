package com.example.biopin.GeoLocationAPI

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://jovpi.up.railway.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GeoLocationService {
    @GET("/")
    suspend fun getLatLng(): GeoLocation
}

object LocationApi {
    val retrofitService: GeoLocationService by lazy {
        retrofit.create(GeoLocationService::class.java)
    }
}