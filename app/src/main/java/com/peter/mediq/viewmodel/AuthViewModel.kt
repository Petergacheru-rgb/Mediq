package com.peter.mediq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peter.mediq.model.User
import com.peter.mediq.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser: StateFlow<User?> = _loggedInUser

    fun registerUser(user: User) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    // Updated login logic to handle password check here.
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)

            if (user != null && user.password == password) {
                _loggedInUser.value = user
            } else {
                _loggedInUser.value = null
            }
        }
    }

    fun logout() {
        _loggedInUser.value = null
    }
}