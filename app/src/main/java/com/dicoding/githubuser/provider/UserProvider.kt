package com.dicoding.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.COMPANY
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.LOCATION
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.NAME
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.PUBLIC_REPOS
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.USERNAME
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion._ID
import com.dicoding.githubuser.database.UserFavorite
import com.dicoding.githubuser.database.UserFavoriteDatabase
import com.dicoding.githubuser.database.UserFavoriteRepository
import kotlinx.coroutines.runBlocking

class UserProvider : ContentProvider() {
    companion object {
        private const val TABLE_NAME = "user_favorite"
        private const val USER = 1
        private const val USER_ID = 2
        const val AUTHORITY = "com.dicoding.githubuser"
        const val SCHEME = "content"
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USER_ID)
        }
    }

    private lateinit var repository: UserFavoriteRepository

    override fun onCreate(): Boolean {
        val userFavoriteDao = UserFavoriteDatabase.getDatabase(context as Context).userFavoriteDao()
        repository = UserFavoriteRepository(userFavoriteDao)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER -> runBlocking { repository.readUserCR() }
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
       return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USER) {
            sUriMatcher.match(uri) -> insertData(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted : Int = when (USER_ID) {
            sUriMatcher.match(uri) -> runBlocking { repository.deleteUserCR(uri) }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    private fun insertData(values: ContentValues?): Long {
        val userFavorite = fromContentValues(values)
        var id = 0L
        if (userFavorite != null) {
            id = runBlocking { repository.addUserCR(userFavorite) }
        }
        return id
    }

    private fun fromContentValues(values: ContentValues?): UserFavorite? {
        if (values != null) {
            return UserFavorite(
                id = values.getAsInteger(_ID),
                name = values.getAsString(NAME),
                username = values.getAsString(USERNAME),
                public_repos = values.getAsString(PUBLIC_REPOS),
                followers = values.getAsString(FOLLOWERS),
                following = values.getAsString(FOLLOWING),
                company = values.getAsString(COMPANY),
                location = values.getAsString(LOCATION),
                avatar_url = values.getAsString(AVATAR_URL)
            )
        }
        return null
    }
}