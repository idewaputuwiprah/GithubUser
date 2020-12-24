package com.dicoding.githubconsumer.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubconsumer.mapping.MappingHelper
import com.dicoding.githubconsumer.userFavorite.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.githubconsumer.userFavorite.UserFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    var mutableReadAllData: MutableLiveData<List<UserFavorite>> = MutableLiveData()
    private var contentResolver: ContentResolver = application.contentResolver

    init {
        setData()
    }

    fun setData() {
        GlobalScope.launch(Dispatchers.Main) {
            val asyncTask = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val user = asyncTask.await()
            mutableReadAllData.value = user
        }
    }

    fun getData(): LiveData<List<UserFavorite>> {
        return mutableReadAllData as LiveData<List<UserFavorite>>
    }

    fun addUserCR(uri: Uri, values: ContentValues) {
        contentResolver.insert(uri, values)
    }

    fun deleteUserCR(uri: Uri) {
        contentResolver.delete(uri, null, null)
    }
}