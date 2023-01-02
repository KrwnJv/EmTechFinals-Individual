package com.example.biopin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.biopin.database.dao.LogsDao
import com.example.biopin.database.dao.UserDao
import com.example.biopin.database.models.Logs
import com.example.biopin.database.models.User

@Database(entities = [User::class, Logs::class], version=1, exportSchema = false)
abstract class BioPinDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun logsDao(): LogsDao

    companion object{
        private var INSTANCE: BioPinDatabase? = null
        fun getDatabase(context: Context): BioPinDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BioPinDatabase::class.java,
                    "biopin_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}