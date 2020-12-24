package com.dicoding.githubconsumer.userFavorite

data class UserFavorite (
    var _id: Int = 0,
    var name: String? = "",
    var username: String? = "",
    var public_repos: String? = "",
    var followers: String? = "",
    var following: String? = "",
    var company: String? = "",
    var location: String? = "",
    var avatar_url: String? = ""
) {

}