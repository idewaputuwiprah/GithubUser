package com.dicoding.githubuser.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class UserColumns: BaseColumns {
        companion object {
            const val _ID = "_id"
            const val NAME = "name"
            const val USERNAME = "username"
            const val PUBLIC_REPOS = "public_repos"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val AVATAR_URL = "avatar_url"
        }
    }
}