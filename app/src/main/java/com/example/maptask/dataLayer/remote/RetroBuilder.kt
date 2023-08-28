package com.example.maptask.dataLayer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class RetroBuilder {

    companion object{

        const val BaseUrl:String =" "

        fun getRetroBuilde():Retrofit{
            return Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}
