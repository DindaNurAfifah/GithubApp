package com.dicoding.submissionone.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.dicoding.submissionone.data.response.ItemsItem
import com.dicoding.submissionone.data.response.UserResponse
import com.dicoding.submissionone.data.retrofit.ApiConfig
import com.dicoding.submissionone.ui.theme.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val preferences: SettingPreferences/*, private val db: Db*/) : ViewModel(){

    companion object{
        private const val TAG = "UserViewModel"
    }

    private val _user = MutableLiveData<List<ItemsItem>>()
    val user: LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUsers("a")
    }

    fun findUsers(query: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items as List<ItemsItem>?

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    class Factory(private val preferences: SettingPreferences/*, private val db: Db*/) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            UserViewModel(preferences/*, db*/) as T
    }

    fun getThemeSettings() = preferences.getThemeSetting().asLiveData()
}