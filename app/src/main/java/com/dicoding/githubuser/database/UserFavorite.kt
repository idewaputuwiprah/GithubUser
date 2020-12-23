package com.dicoding.githubuser.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favorite")
data class UserFavorite (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String?,
    val username: String?,
    val public_repos: String?,
    val followers: String?,
    val following: String?,
    val company: String?,
    val location: String?,
    val avatar_url: String?
)