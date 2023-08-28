package com.example.maptask.dataLayer.model


import retrofit2.http.Url


data class LoginResponse (val id:Int,
                          val first_name:String,
                          val last_name:String,
                          val mobile:String,
                          val email:String,
                          val avatar: Url?,
                          val access_token:String,
                          val lang:String,
                          val rating:Int,
                          val created_at:Int,
                          val timezone:String,
                          val zone_id:String,
                          val is_email_verified:String,
)