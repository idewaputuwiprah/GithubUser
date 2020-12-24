package com.dicoding.githubuser.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {
    @Insert
    fun addUserCR(userFavorite: UserFavorite): Long

    @Query("DELETE FROM user_favorite WHERE _id = :id")
    fun deleteUserById(id: String): Int

    @Query("SELECT * FROM user_favorite")
    fun readAllDataCursor(): Cursor
}