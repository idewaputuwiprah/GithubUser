package com.dicoding.githubuser.mapping

import android.database.Cursor
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion._ID
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.COMPANY
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.FOLLOWERS
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.FOLLOWING
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.LOCATION
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.NAME
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.PUBLIC_REPOS
import com.dicoding.githubuser.database.DatabaseContract.UserColumns.Companion.USERNAME
import com.dicoding.githubuser.database.UserFavorite

object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<UserFavorite> {
        val userList = ArrayList<UserFavorite>()

        userCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val name = getString(getColumnIndexOrThrow(NAME))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val publicRepos = getString(getColumnIndexOrThrow(PUBLIC_REPOS))
                val followers = getString(getColumnIndexOrThrow(FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(FOLLOWING))
                val company = getString(getColumnIndexOrThrow(COMPANY))
                val location = getString(getColumnIndexOrThrow(LOCATION))
                val avatarURL = getString(getColumnIndexOrThrow(AVATAR_URL))
                userList.add(
                    UserFavorite(
                    id = id, name = name, username = username, public_repos = publicRepos, followers = followers,
                        following = following, company = company, location = location, avatar_url = avatarURL
                ))
            }
        }
        return userList
    }
}