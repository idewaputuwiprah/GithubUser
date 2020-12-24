package com.dicoding.githubuser.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.COMPANY
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.LOCATION
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.NAME
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.PUBLIC_REPOS
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.USERNAME
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion._ID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserFavoriteRepository(private val userFavoriteDao: UserFavoriteDao) {
    private var myDispatcher: CoroutineDispatcher = Dispatchers.IO

    //content provider
    suspend fun readUserCR(): Cursor {
        return withContext(myDispatcher) {
            userFavoriteDao.readAllDataCursor()
        }
    }

    suspend fun addUserCR(userFavorite: UserFavorite): Long {
        return withContext(myDispatcher) {
            userFavoriteDao.addUserCR(userFavorite)
        }
    }

    suspend fun deleteUserCR(uri: Uri): Int {
        return withContext(myDispatcher) {
            userFavoriteDao.deleteUserById(uri.lastPathSegment.toString())
        }
    }
}