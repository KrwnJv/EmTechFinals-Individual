package com.example.biopin.viewmodel

import androidx.lifecycle.*
import com.example.biopin.database.dao.UserDao
import com.example.biopin.database.models.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthViewModel(private val userDao: UserDao): ViewModel() {

    private val _user = MutableLiveData<String>()

    private suspend fun getUser(username: String, password: String): User? = userDao.getUser(username = username, password = password)

    fun userValidation(username: String, password: String): Boolean {
        val user: User? = runBlocking { getUser(username, password) }
        if (user != null) {
            _user.value = user.username
            return true
        }
        return false
    }

    private fun insertUser(user: User){
        viewModelScope.launch{
            userDao.insertUser(
                user = user
            )
        }
    }

    private fun createNewUser(username: String, password: String, firstName: String, lastName: String): User {
        return User(
            username = username,
            password = password,
            firstName = firstName,
            lastName = lastName
        )
    }

    fun entryValidationRegister(username: String, password: String, firstName: String, lastName: String, confirmPassword: String): Boolean {
        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank() || firstName.isBlank() || lastName.isBlank())
            return false
        return true
    }

    fun entryValidationLogin(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank())
            return false
        return true
    }

    fun passwordSimilarity(password: String, confirmPassword: String): Boolean {
        if (password == confirmPassword)
            return true
        return false
    }

    fun newUserEntry(username: String, password: String, firstName: String, lastName: String) {
        val user = createNewUser(username, password, firstName, lastName)
        insertUser(user)
    }
}

class AuthViewModelFactory(private val userDao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
