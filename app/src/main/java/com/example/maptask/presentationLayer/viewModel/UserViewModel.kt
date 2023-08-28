package com.example.maptask.presentationLayer.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.*
import com.example.maptask.dataLayer.model.*

import com.example.maptask.dataLayer.remote.RetroBuilder
import com.example.maptask.dataLayer.remote.ServiceAPI
import com.example.maptask.dataLayer.repo.UserRepoAPI
import com.example.maptask.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
@SuppressLint("StaticFieldLeak")
class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val ctx: Context =application.baseContext
    private lateinit var userRepoAPI: UserRepoAPI

    private var getUserMutableLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> get() = getUserMutableLiveData

    private var sendMutableLive = MutableLiveData<Response<ResultSend>>()
    val sendLive: LiveData<Response<ResultSend>> = sendMutableLive


    private var checkMutableLive = MutableLiveData<Response<CheckResponse>>()
    val checkLive: LiveData<Response<CheckResponse>> = checkMutableLive
    private var registerMutableLive = MutableLiveData<Response<LoginResponse>>()
    val registerLive: LiveData<Response<LoginResponse>> = registerMutableLive


    private var emilMutableLive = MutableLiveData<Response<CheckResponseEmial>>()
    val emilLive: LiveData<Response<CheckResponseEmial>> = emilMutableLive


    private var logoutMutableLive = MutableLiveData<Response<logoutMessage>>()
    val logoutLive: LiveData<Response<logoutMessage>> = logoutMutableLive


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val serviceInstance = RetroBuilder.getRetroBuilde().create(ServiceAPI::class.java)
            userRepoAPI = UserRepoAPI(serviceInstance)

        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun send(token:String,email: String, mobile: String, type: String) {
        viewModelScope.launch(Dispatchers.Main) {
            sendMutableLive.postValue(userRepoAPI.send(token,email, mobile, type))
        }
    }

    fun check(token:String,
              type: String,
              email: String,
              mobile: String,
              otp: String
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            checkMutableLive.postValue(userRepoAPI.check(token,type, email, mobile, otp))
        }
    }

    fun email(token: String, otp_token: String){

        viewModelScope.launch(Dispatchers.Main) {


            emilMutableLive.postValue(userRepoAPI.email(token, otp_token))
        }
    }
    fun logout(token: String){
        viewModelScope.launch(Dispatchers.Main) {
            logoutMutableLive.postValue(userRepoAPI.logout(token))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun register(
        device_id: String,
        device_token: String,
        first_name: String,
        last_name: String,
        email:String,
        lat: Float,
        lng: Float,
        password: String,
        avatar: Uri,
        otp_token: String
    ) {
        val aa =prepareFilePart("avatar",  avatar)

        val device_id1: RequestBody = device_id.toRequestBody("text/plain".toMediaTypeOrNull())

        viewModelScope.launch(Dispatchers.Main) {


                    registerMutableLive.postValue(userRepoAPI.register(
                        device_id.toRequestBody("text/plain".toMediaTypeOrNull()),
                        device_token.toRequestBody("text/plain".toMediaTypeOrNull()),
                        first_name.toRequestBody("text/plain".toMediaTypeOrNull()),
                        last_name.toRequestBody("text/plain".toMediaTypeOrNull()),
                        email.toRequestBody("text/plain".toMediaTypeOrNull()),
                        lat,
                        lng,
                        password.toRequestBody("text/plain".toMediaTypeOrNull()),
                        aa,
                        otp_token.toRequestBody("text/plain".toMediaTypeOrNull())
                    ))

            }

        }
     fun prepareFilePart(partName: String,  fileUri: Uri): MultipartBody.Part {
        val file: File = FileUtils.getFile(ctx, fileUri)
        val requestFile: RequestBody = RequestBody.create(
            ctx.contentResolver.getType(fileUri)!!.toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
    }








