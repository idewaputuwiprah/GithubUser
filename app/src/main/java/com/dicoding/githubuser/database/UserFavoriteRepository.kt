package com.dicoding.githubuser.database

import androidx.lifecycle.LiveData

class UserFavoriteRepository(private val userFavoriteDao: UserFavoriteDao) {
    val readAllData: LiveData<List<UserFavorite>> = userFavoriteDao.readAllData()

    suspend fun addUser(userFavorite: UserFavorite) {
        userFavoriteDao.addUser(userFavorite)
    }

    suspend fun deleteUser(userFavorite: UserFavorite) {
        userFavoriteDao.deleteUser(userFavorite)
    }
}