package com.dicoding.githubconsumer.retrofit

data class UserDetailResponse(
    val login: String? = "",
    val id: Int? = 0,
    val avatar_url: String? = "",
    val type: String? = "",
    val public_repos: Int? = 0,
    val followers: Int? = 0,
    val following: Int? = 0,
    val name: String? = "",
    val company: String? = "",
    val location: String? = ""
)