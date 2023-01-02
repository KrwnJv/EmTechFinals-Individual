package com.example.biopin.database.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val username: String,
    @NonNull @ColumnInfo
    val password: String,
    @NonNull @ColumnInfo
    val firstName: String,
    @NonNull @ColumnInfo
    val lastName: String
)
