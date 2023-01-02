package com.example.biopin.database.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
data class Logs(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val User: String,
    @ColumnInfo
    val Remarks: String,
    @ColumnInfo
    val Coordinates: String
)