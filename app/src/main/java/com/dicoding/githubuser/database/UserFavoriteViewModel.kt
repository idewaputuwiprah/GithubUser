package com.dicoding.githubuser.database

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuser.mapping.MappingHelper
import com.dicoding.githubuser.provider.UserProvider.Companion.CONTENT_URI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserFavoriteViewModel(application: Application): AndroidViewModel(application) {
    var mutableReadAllData: MutableLiveData<List<UserFavorite>> = MutableLiveData()
    private var contentResolver: ContentResolver = application.contentResolver

    init {
        setData()
    }

    //with content provider
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
        return mutableReadAllData
    }

    fun addUserCR(uri: Uri, values: ContentValues) {
        contentResolver.insert(uri, values)
    }

    fun deleteUserCR(uri: Uri) {
        contentResolver.delete(uri, null, null)
    }
}