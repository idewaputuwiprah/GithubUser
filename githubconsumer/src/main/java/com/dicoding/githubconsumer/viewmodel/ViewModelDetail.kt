package com.dicoding.githubconsumer.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubconsumer.BuildConfig
import com.dicoding.githubconsumer.retrofit.ApiService
import com.dicoding.githubconsumer.retrofit.UserDetailResponse
import com.dicoding.githubconsumer.retrofit.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelDetail: ViewModel() {

    private val listFollowers = MutableLiveData<ArrayList<UserResponse>>()
    private val listFollowing = MutableLiveData<ArrayList<UserResponse>>()
    private val user = MutableLiveData<UserDetailResponse>()
    private val token = "token ${BuildConfig.GITHUB_TOKEN}"

    fun setFollowers(username: String) {

        ApiService.endpoint.getFollowers(token, username).enqueue(object: Callback<List<UserResponse>>{
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                Log.d("onResponse", response.code().toString())
                val result = response.body() as ArrayList<UserResponse>
                listFollowers.postValue(result)
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.d("onFailure", t.message as String)
            }
        })
    }

    fun getFollowers(): MutableLiveData<ArrayList<UserResponse>> {
        return listFollowers
    }

    fun setFollowing(username: String) {
        ApiService.endpoint.getFollowing(token, username).enqueue(object: Callback<List<UserResponse>>{
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                Log.d("onResponse", response.code().toString())
                val result = response.body() as ArrayList<UserResponse>
                listFollowing.postValue(result)
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                Log.d("onFailure", t.message as String)
            }

        })
    }

    fun getFollowing(): MutableLiveData<ArrayList<UserResponse>> {
        return listFollowing
    }

    fun setUserDetail(username: String) {
        ApiService.endpoint.detailUsers(username).enqueue(object: Callback<UserDetailResponse>{
            override fun onResponse(call: Call<UserDetailResponse>, response: Response<UserDetailResponse>) {
                Log.d("onResponse", response.code().toString())
                val result = response.body() as UserDetailResponse
                user.postValue(result)
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.d("onFailure", t.message as String)
            }
        })
    }

    fun getUserDetail(): MutableLiveData<UserDetailResponse> {
        return user
    }
}