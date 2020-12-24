package com.dicoding.githubuser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.COMPANY
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.LOCATION
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.NAME
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.PUBLIC_REPOS
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.USERNAME
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion._ID

@Entity(tableName = "user_favorite")
data class UserFavorite (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = _ID)
    var id: Int = 0,

    @ColumnInfo(name = NAME)
    var name: String? = "",

    @ColumnInfo(name = USERNAME)
    var username: String? = "",

    @ColumnInfo(name = PUBLIC_REPOS)
    var public_repos: String? = "",

    @ColumnInfo(name = FOLLOWERS)
    var followers: String? = "",

    @ColumnInfo(name = FOLLOWING)
    var following: String? = "",

    @ColumnInfo(name = COMPANY)
    var company: String? = "",

    @ColumnInfo(name = LOCATION)
    var location: String? = "",

    @ColumnInfo(name = AVATAR_URL)
    var avatar_url: String? = ""
)