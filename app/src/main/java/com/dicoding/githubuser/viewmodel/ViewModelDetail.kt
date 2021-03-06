package com.dicoding.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.retrofit.ApiService
import com.dicoding.githubuser.retrofit.UserDetailResponse
import com.dicoding.githubuser.retrofit.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelDetail: ViewModel() {

    private val listFollowers = MutableLiveData<ArrayList<UserResponse>>()
    private val listFollowing = MutableLiveData<ArrayList<UserResponse>>()
    private val user = MutableLiveData<UserDetailResponse>()
    private val token = "token bd285bb153303924cf64012554796f968bc06ca2"

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