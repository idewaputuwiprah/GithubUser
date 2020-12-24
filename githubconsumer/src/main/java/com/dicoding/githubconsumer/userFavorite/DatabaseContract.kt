package com.dicoding.githubconsumer.userFavorite

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.dicoding.githubuser"
    const val SCHEME = "content"

    internal class UserColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "user_favorite"
            const val _ID = "_id"
            const val NAME = "name"
            const val USERNAME = "username"
            const val PUBLIC_REPOS = "public_repos"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}