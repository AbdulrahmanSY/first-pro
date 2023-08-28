package com.example.maptask.dataLayer.model

data class LoginRequest(val device_id:String,
                        val info:String,
                        val device_token:String,
                        val otp_token:String,
                        val lang:String
)