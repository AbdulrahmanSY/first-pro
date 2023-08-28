package com.example.maptask.dataLayer.repo


import com.example.maptask.dataLayer.model.CheckResponse
import com.example.maptask.dataLayer.model.ResultSend
import com.example.maptask.dataLayer.model.User
import com.example.maptask.dataLayer.model.logoutMessage
import retrofit2.Response


interface UserRepoInterface {

    suspend fun getUserAPI(id:Int): Response<User>
    suspend fun updateUserAPI(user: User, id: Int): Response<User>

    suspend fun send(token:String,email: String, mobile: String,type: String):Response<ResultSend>
    suspend fun check(token:String, type:String,email:String, mobile:String,otp:String): Response<CheckResponse>
    suspend fun logout(token:String) :Response<logoutMessage>

}