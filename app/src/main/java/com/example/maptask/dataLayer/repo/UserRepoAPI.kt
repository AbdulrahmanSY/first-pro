package com.example.maptask.dataLayer.repo

import com.example.maptask.dataLayer.model.*

import com.example.maptask.dataLayer.remote.ServiceAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class UserRepoAPI(private val api: ServiceAPI): UserRepoInterface, ServiceAPI {


    override suspend fun getUserAPI(
        id:Int
    ): Response<User> = withContext(Dispatchers.IO){
            api.getUserAPI(id)

        }


    override suspend fun updateUserAPI(
        user: User,
        id: Int
    ): Response<User>
        = withContext(Dispatchers.IO) {
        api.updateUserAPI(user,id)

    }



    override suspend fun send(
        token:String,
        email: String,
        mobile: String,
        type: String
    ): Response<ResultSend>
            = withContext(Dispatchers.IO) {
        api.send(token,email,mobile,type)
    }

    override suspend fun check(
        token: String,
        type: String,
        email: String,
        mobile: String,
        otp: String
    ): Response<CheckResponse> = withContext(Dispatchers.IO){

        api.check(token,type,email,mobile,otp)
    }

    override suspend fun logout(token: String):Response<logoutMessage> =withContext(Dispatchers.IO){

        api.logout(token)
    }


    override suspend fun register(
         device_id: RequestBody?,
         device_token: RequestBody?,
         first_name: RequestBody?,
         last_name: RequestBody?,
         email: RequestBody?,
         lat: Float?,
         lng: Float?,
         password: RequestBody?,
         avatar: MultipartBody.Part?,
         otp_token: RequestBody?
    ): Response<LoginResponse> = withContext(Dispatchers.IO){
        api.register(device_id,device_token,first_name,last_name,email,lat
        ,lng,password,avatar,otp_token)
    }

    override suspend fun email(token: String, otp_token: String): Response<CheckResponseEmial> = withContext(Dispatchers.IO){
        api.email(token,otp_token)
    }



//    override suspend fun re(avatar: MultipartBody.Part): Response<LoginResponse> = withContext(Dispatchers.IO){
//        api.re(avatar)
//    }

}