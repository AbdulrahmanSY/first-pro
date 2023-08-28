package com.example.maptask.dataLayer.remote

import com.example.maptask.dataLayer.model.*

import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Response
import retrofit2.http.*

interface ServiceAPI {

    @POST("/api/v4/driver/otp/send")
    @FormUrlEncoded

    @Headers("Accept: application/json","api-key:h}CPk9rm`L","api-secret:@E@En4q_+^J;Ua*q>3?t<D+")
     suspend fun send(@Header("Authorization")token:String,
                      @Field("email") email:String,
                      @Field("mobile")mobile:String,
                      @Field("type") type:String
    ): Response<ResultSend>



    @POST("/api/v4/driver/otp/check")
    @FormUrlEncoded
    @Headers("Accept: application/json","api-key:h}CPk9rm`L","api-secret:@E@En4q_+^J;Ua*q>3?t<D+")

    suspend fun check(@Header("Authorization")token:String,
                      @Field("type") type:String,
                      @Field("email")email:String,
                      @Field("mobile")mobile:String,
                      @Field("otp")otp:String
    ): Response<CheckResponse>



    @POST("/api/v4/driver/account/register")
//    @FormUrlEncoded
    @Multipart
    @Headers("Accept: application/json","api-key:h}CPk9rm`L","api-secret:@E@En4q_+^J;Ua*q>3?t<D+","Device-Type:android","Accept-Language:en")
    suspend fun register(@Part("device_id")device_id: RequestBody?,
                         @Part("device_token")device_token:RequestBody?,
                         @Part("first_name")first_name:RequestBody?,
                         @Part("last_name")last_name:RequestBody?,
                         @Part("email")email:RequestBody?,
                         @Part("lat")lat:Float?,
                         @Part("lng")lng:Float?,
                         @Part("password")password :RequestBody?,
                         @Part avatar:MultipartBody.Part?,
                         @Part("otp_token")otp_token:RequestBody?,
    ): Response<LoginResponse>


    @POST("/api/v4/driver/account/email")
    @FormUrlEncoded
    @Headers("Accept: application/json","api-key:h}CPk9rm`L","api-secret:@E@En4q_+^J;Ua*q>3?t<D+","Device-Type:android","Accept-Language:en")
    suspend fun email(@Header("Authorization")token:String,
                      @Field("otp")otp_token:String

    ): Response<CheckResponseEmial>

    @POST("/api/v4/driver/logout")
    @FormUrlEncoded
    @Headers("Accept: application/json","api-key:h}CPk9rm`L","api-secret:@E@En4q_+^J;Ua*q>3?t<D+","Device-Type:android","Accept-Language:en")
    suspend fun logout(@Header("Authorization")token:String,
    ): Response<logoutMessage>














    @GET("/AbdulRahmanSY/jsonplaceholder/users")
    suspend fun getUserAPI(@Path("id")id: Int): Response<User>

    @PUT("/AbdulRahmanSY/jsonplaceholder/users")
    suspend fun updateUserAPI(@Body user: User, @Path("id")id: Int): Response<User>
}