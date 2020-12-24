package com.dicoding.githubconsumer.retrofit

data class UserDetailResponse(
    val login: String?,
    val id: Int?,
    val avatar_url: String?,
    val type: String?,
    val public_repos: Int?,
    val followers: Int?,
    val following: Int?,
    val name: String?,
    val company: String?,
    val location: String?
)