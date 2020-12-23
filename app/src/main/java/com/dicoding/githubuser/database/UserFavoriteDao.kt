package com.dicoding.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {
    @Insert
    suspend fun addUser(userFavorite: UserFavorite)

    @Delete
    suspend fun deleteUser(userFavorite: UserFavorite)

    @Query("SELECT * FROM user_favorite ORDER BY id ASC")
    fun readAllData(): LiveData<List<UserFavorite>>
}