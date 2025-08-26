package com.peter.mediq.repository

import com.peter.mediq.data.UserDao
import com.peter.mediq.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    // This method now calls the new getUserByEmail function.
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}