package com.dicoding.githubuser.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFavoriteViewModel(application: Application): AndroidViewModel(application) {
    var readAllData: LiveData<List<UserFavorite>>
    private var repository: UserFavoriteRepository

    init {
        val userFavoriteDao = UserFavoriteDatabase.getDatabase(application).userFavoriteDao()
        repository = UserFavoriteRepository(userFavoriteDao)
        readAllData = repository.readAllData
    }

    fun addUser(userFavorite: UserFavorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(userFavorite)
        }
    }

    fun deleteUser(userFavorite: UserFavorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userFavorite)
        }
    }
}