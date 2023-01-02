package com.example.biopin.database.dao

import androidx.room.*
import com.example.biopin.database.models.Logs
import kotlinx.coroutines.flow.Flow


@Dao
interface LogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(Logs: Logs)

    @Update
    fun update(Logs: Logs)

    @Delete
    fun delete(Logs: Logs)

    @Query("SELECT * FROM Logs")
    fun getAll(): Flow<List<Logs>>

    @Query("SELECT * FROM Logs WHERE id = :id LIMIT 1")
    fun getLog(id: Int): Flow<Logs>
}