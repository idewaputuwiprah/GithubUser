package com.dicoding.githubuser.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("users/{username}/followers")
    fun getFollowers(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("search/users")
    fun searchUsers(
        @Header("Authorization") token: String,
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun detailUsers(
        @Path("username") username: String
    ): Call<UserDetailResponse>
}