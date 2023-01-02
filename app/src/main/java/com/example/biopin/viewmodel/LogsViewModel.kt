package com.example.biopin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.biopin.database.dao.LogsDao
import com.example.biopin.database.models.Logs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class LogsViewModel(private val logsDao: LogsDao): ViewModel() {

    private fun createLogs(Logs: Logs){
        logsDao.create(Logs)
    }

    private fun updateLogDao(logs: Logs){
        viewModelScope.launch {
            logsDao.update(logs)
        }
    }

    private fun createNewLog(User: String, Remarks: String, Coordinates: String): Logs {
        return Logs(
            User = User,
            Remarks = Remarks,
            Coordinates = Coordinates
        )
    }

    private fun updateExistingLog(id: Int, User: String, Remarks: String, Coordinates: String): Logs {
        return Logs(
            id = id,
            User = User,
            Remarks = Remarks,
            Coordinates = Coordinates
        )
    }

    fun newLogEntry(User: String, Remarks: String, Coordinates: String) {
        val logs = createNewLog(User, Remarks, Coordinates)
        createLogs(logs)
    }

    fun getAllLogs(): Flow<List<Logs>> = logsDao.getAll()

    fun deleteLog(logs: Logs){
        viewModelScope.launch {
            logsDao.delete(logs)
        }
    }

    fun getLog(id: Int): Flow<Logs> = logsDao.getLog(id)

    fun updateLog(id: Int, User: String, Remarks: String, Coordinates: String){
        val logs = updateExistingLog(id, User, Remarks, Coordinates)
        updateLogDao(logs)
    }

}

class LogsViewModelFactory(private val logsDao: LogsDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LogsViewModel(logsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
