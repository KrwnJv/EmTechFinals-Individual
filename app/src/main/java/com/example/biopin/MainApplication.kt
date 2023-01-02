package com.example.biopin

import com.example.biopin.database.BioPinDatabase
import android.app.Application

class MainApplication: Application() {
    val database: BioPinDatabase by lazy {
        BioPinDatabase.getDatabase(this)
    }
}