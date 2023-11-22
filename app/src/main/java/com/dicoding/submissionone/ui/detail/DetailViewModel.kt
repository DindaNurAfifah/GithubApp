package com.dicoding.submissionone.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.submissionone.data.database.Note
import com.dicoding.submissionone.data.database.NoteDao
import com.dicoding.submissionone.data.database.UserRoomDatabase
import com.dicoding.submissionone.data.response.DetailUserResponse
import com.dicoding.submissionone.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private var userDao: NoteDao?
    private var userDb: UserRoomDatabase?
    init {
        userDb = UserRoomDatabase.getInstance(application)
        userDao = userDb?.noteDao()
    }

    fun findDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun insert(username: String, avatarUrl: String, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = Note(
                username,
                avatarUrl,
                id
            )
            userDao?.insert(user)
        }
    }

    fun checkFavorite(id: Int) = userDao?.checkFavorite(id)

    fun remove(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.remove(id)
        }
    }
}