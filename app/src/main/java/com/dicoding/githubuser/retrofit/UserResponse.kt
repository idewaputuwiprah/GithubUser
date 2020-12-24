package com.dicoding.githubuser.retrofit

data class UserResponse(
    val login: String? = "",
    val id: Int? = 0,
    val avatar_url: String? = "",
    val type: String? = ""
)