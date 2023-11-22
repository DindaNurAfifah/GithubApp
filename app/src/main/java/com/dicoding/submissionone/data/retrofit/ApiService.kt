package com.dicoding.submissionone.data.retrofit

import com.dicoding.submissionone.data.response.DetailUserResponse
import com.dicoding.submissionone.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*
import com.dicoding.submissionone.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") username: String): Call<UserResponse>

    @GET("users/{username}")
    fun getDetail(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}

