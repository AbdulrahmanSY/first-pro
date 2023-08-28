package com.example.maptask.dataLayer.model

import android.media.Image

data class RegisterRequest (var device_token:String,
                            var device_id:String,
                            var first_name:String,
                            var last_name:String,
                            var email:String,
                            var lat:Float,
                            var lng:Float,
                            var otp_token:String,
                            var timezone:String,
                            var avatar:Image
                     )