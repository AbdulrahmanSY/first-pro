package com.example.maptask.presentationLayer.userInterface

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.maptask.databinding.ActivityRegisterBinding
import com.example.maptask.presentationLayer.viewModel.UserViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.xml.validation.Validator


@Suppress("DEPRECATION")
class Register : AppCompatActivity() {
companion object{
    var a:Int?=0
}
    private lateinit var binding:ActivityRegisterBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var latitude:Float? = 0.0f
    var longiitude:Float? = 0.0f
    private lateinit var fname:String
    private lateinit var lname:String
    private lateinit var email:String
    private lateinit var phoneNumber:String
    private lateinit var password:String
    private var type:String = "REGISTER"
    lateinit var viewModel: UserViewModel
    lateinit var selectedImageUri: Uri
    private var bmp: Bitmap? = null
    lateinit var sharedPreferences:SharedPreferences

    private val resultImage =  registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri= result.data!!.data!!
            binding.IVRegester.setImageURI(selectedImageUri)
            Toast.makeText(this@Register,selectedImageUri.toString(),Toast.LENGTH_LONG).show()

        }
    }



    @SuppressLint("CommitPrefEdits", "HardwareIds", "SuspiciousIndentation")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)




















// viewModel
        viewModel = ViewModelProvider.AndroidViewModelFactory(this.application).create(UserViewModel::class.java)
//sharedPreferences

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)



        val startForResultEmail = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK){
                    val token =sharedPreferences.getString("access_token", "access_token").toString()
                try {
                    viewModel.email(
                        token,
                        result.data!!.getStringExtra("token_email").toString()
                    )
                    binding.progressBarOtpMobileRegister.visibility = View.VISIBLE
                    viewModel.emilLive.observe(/* owner = */
                        this@Register,
                        Observer { E ->
                            Toast.makeText(
                                this@Register,
                                "verfiy emial successfully ",
                                Toast.LENGTH_LONG
                            )
                                .show()

                            if (E.isSuccessful) {

                                Toast.makeText(
                                    this@Register,
                                    "Wellcome to project ",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                val intent = Intent(this@Register, MapsActivity::class.java)
                                 intent.putExtra("access_token",token)
                                startActivity(intent)
                            } else if(E.code()==403){
                                val jsonObj = JSONObject(E.errorBody()!!.charStream().readText())
                                Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG).show()
                                Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG).show()


                                    val intent = Intent(this@Register, MapsActivity::class.java)
                                    intent.putExtra("access_token",token)
                                    startActivity(intent)
                            }
                            else{
                                Toast.makeText(this@Register,"Errorrrrrrrrrrrrrrrrrrrr in Email", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@Register, MapsActivity::class.java)
                                    intent.putExtra("access_token",token)
                                    startActivity(intent)
                            }
                        })
                }catch (e:Exception){

                    Toast.makeText(this@Register,e.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(this@Register,e.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(this@Register,e.message, Toast.LENGTH_LONG).show()
                    Toast.makeText(this@Register,e.message, Toast.LENGTH_LONG).show()
                }



            }

        }

        val startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this@Register, "now register ", Toast.LENGTH_LONG).show()
                sharedPreferences.edit().putString("otp_token",result.data!!.getStringExtra("token")).apply()

                        binding.progressBarOtpEmailRegister.visibility = View.VISIBLE
                    try {
                        viewModel.register(
                            sharedPreferences.getString("deviceId", "deviceId").toString(),
                            "4321",
                            sharedPreferences.getString("fname", "fname").toString(),
                            sharedPreferences.getString("lname", "lname").toString(),
                            sharedPreferences.getString("email", "email").toString(),
                            41f,
                            29f,
                            sharedPreferences.getString("password", "password").toString(),
                            selectedImageUri,
                            sharedPreferences.getString("otp_token", "otp_token").toString()
                        )
                        viewModel.registerLive.observe(this@Register, Observer { send ->
                                    binding.progressBarOtpEmailRegister.visibility = View.GONE

                                    Toast.makeText(this@Register, send.isSuccessful.toString(), Toast.LENGTH_LONG).show()
                                    Toast.makeText(this@Register, send.code().toString(), Toast.LENGTH_LONG).show()
                                    if (send.isSuccessful) {
                                        Toast.makeText(
                                            this@Register,
                                            send.body()!!.id.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            this@Register,
                                            send.body()!!.first_name,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            this@Register,
                                            send.body()!!.last_name,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(this@Register, send.body()!!.lang, Toast.LENGTH_LONG)
                                            .show()
                                        Toast.makeText(this@Register, send.body()!!.lang, Toast.LENGTH_LONG)
                                            .show()
                                        Toast.makeText(
                                            this@Register,
                                            send.body()!!.email,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            this@Register,
                                            send.body()!!.access_token,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            this@Register,
                                            send.body()!!.id.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            this@Register,
                                            send.body()!!.id.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            this@Register,
                                            send.body()!!.id.toString(),
                                            Toast.LENGTH_LONG
                                        ).show()

                                        sharedPreferences.edit().putString("access_token",send.body()!!.access_token).apply()
                                        sharedPreferences.edit().putInt("id",send.body()!!.id).apply()

//                                        binding.btnNextRegister.setOnClickListener(){

                                                    Toast.makeText(this@Register, "Email verfiy ", Toast.LENGTH_LONG).show()
                                            try{
                                                    viewModel.send(send.body()!!.access_token,email,phoneNumber,"UPDATE_EMAIL")
                                                    binding.progressBarOtpMobileRegister.visibility = View.VISIBLE
                                                    viewModel.sendLive.observe(this@Register, Observer { s->
                                                        Toast.makeText(this@Register, send.isSuccessful.toString(), Toast.LENGTH_LONG).show()
                                                        if(s.isSuccessful){
                                                            val intent = Intent(this@Register,OTP::class.java)
                                                            intent.putExtra("to",s.body()?.to)
                                                            intent.putExtra("typ","UPDATE_EMAIL")
                                                            intent.putExtra("access_token",send.body()!!.access_token)
                                                            intent.putExtra("phoneNumber",phoneNumber)
                                                            intent.putExtra("email",email)
                                                            intent.putExtra("resend_cooldown",s.body()?.resend_cooldown)
                                                            startForResultEmail.launch(intent)
                                                            binding.progressBarOtpMobileRegister.visibility = View.GONE
                                                        }
                                                        else
                                                        {
        ////                                        val jsonObj = JSONObject(send.errorBody()!!.charStream().readText())
        //                                            Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG).show()
        //                                            Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG).show()
                                                            binding.progressBarOtpMobileRegister.visibility = View.GONE
                                                        }
                                                        binding.progressBarOtpMobileRegister.visibility = View.GONE
                                                    })

                                                }
                                                catch (e:Exception){
                                                    Toast.makeText(this@Register,"catch your error",Toast.LENGTH_LONG).show()
                                                    Toast.makeText(this@Register,e.message.toString(),Toast.LENGTH_LONG).show()
                                                    Toast.makeText(this@Register,e.message.toString(),Toast.LENGTH_LONG).show()
                                                    binding.progressBarOtpMobileRegister.visibility = View.GONE
                                                }




//                                    //}
                                    } else {
                                        Toast.makeText(
                                            this@Register,
                                            send.body()?.id.toString(),
                                            Toast.LENGTH_LONG

                                        ).show()
                                        val jsonObj = JSONObject(send.errorBody()!!.charStream().readText())
                                        Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG)
                                            .show()
                                        Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG)
                                            .show()
                                        Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG)
                                            .show()
                                        Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG)
                                            .show()
                                        Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG)
                                            .show()


                                    }

                                    binding.progressBarOtpMobileRegister.visibility = View.GONE
                        })

//                }
                    } catch (e: Exception) {
                        Toast.makeText(this@Register, e.message.toString(), Toast.LENGTH_LONG)
                            .show()
                        binding.progressBarOtpMobileRegister.visibility = View.GONE
                }
        }
        }


//getDeviceId
        val deviceId = Settings.Secure.getString(contentResolver,Settings.Secure.ANDROID_ID)
            sharedPreferences.edit().putString("deviceId",deviceId).apply()
        Toast.makeText(this,deviceId,Toast.LENGTH_LONG).show()
//getLocation
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
//validate
        fisrtNameFocusListener()
        lastNameFocusListener()
        phoneFocusListener()
        emailFocusListener()
        passwordFocusListener()

        binding.btnNextRegister.setOnClickListener(){

        fname = binding.editFirstNameRegister.text.toString()
        lname = binding.editLastNameRegister.text.toString()
        email = binding.editEmailRegister.text.toString()
        password = binding.editPasswordRegister.text.toString()
        phoneNumber = binding.countryCode.textView_selectedCountry.text.toString().plus(binding.editPhoneNumberRegister.text.toString() )




                    try {
                            if(isValidateName(fname) && isValidateName(lname) && isValidEmail(email) && isValidPhoneNumber(phoneNumber) && validPassword(password) == null){
                                sharedPreferences.edit().putString("fname",fname).apply()
                                sharedPreferences.edit().putString("lname",lname).apply()
                                sharedPreferences.edit().putString("email",email).apply()
                                sharedPreferences.edit().putString("password",password).apply()
                                sharedPreferences.edit().putString("selectedCountry",binding.countryCode.textView_selectedCountry.text.toString()).apply()
                                sharedPreferences.edit().putString("editPhoneNumberRegister",binding.editPhoneNumberRegister.text.toString()).apply()
                                sharedPreferences.edit().putString("phoneNumber",phoneNumber).apply()

                                binding.editFirstNameRegister.setText(sharedPreferences.getString("fname","fname").toString())
                                binding.editLastNameRegister.setText(sharedPreferences.getString("lname","lname").toString())
                                binding.editEmailRegister.setText(sharedPreferences.getString("email","email").toString())
                                binding.editPasswordRegister.setText(sharedPreferences.getString("password","password").toString())
//                                binding.editPhoneNumberRegister.setText(sharedPreferences.getString("editPhoneNumberRegister","editPhoneNumberRegister").toString())
                                viewModel.send("rfvfsdfd",email,phoneNumber,type)
                                binding.progressBarOtpMobileRegister.visibility = View.VISIBLE
                                viewModel.sendLive.observe(this@Register, Observer { send ->
                                    Toast.makeText(this@Register, send.isSuccessful.toString(), Toast.LENGTH_LONG).show()
                                    if(send.isSuccessful){
                                        val intent = Intent(this@Register,OTP::class.java)
                                        intent.putExtra("to",send.body()?.to)
                                        intent.putExtra("typ",type)
                                        intent.putExtra("phoneNumber",phoneNumber)
                                        intent.putExtra("email",email)
                                        intent.putExtra("resend_cooldown",send.body()?.resend_cooldown)
                                        startForResult.launch(intent)
                                        binding.progressBarOtpMobileRegister.visibility = View.GONE
                                    }
                                    else
                                    {
////                                        val jsonObj = JSONObject(send.errorBody()!!.charStream().readText())
//                                            Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG).show()
//                                            Toast.makeText(this@Register, jsonObj.toString(), Toast.LENGTH_LONG).show()
                                            binding.progressBarOtpMobileRegister.visibility = View.GONE
                                    }
                                    binding.progressBarOtpMobileRegister.visibility = View.GONE
                                })

                            }


                    }
                    catch (e:Exception){
                        Toast.makeText(this@Register,"catch your error",Toast.LENGTH_LONG).show()
                        Toast.makeText(this@Register,e.message.toString(),Toast.LENGTH_LONG).show()
                        Toast.makeText(this@Register,e.message.toString(),Toast.LENGTH_LONG).show()
                        binding.progressBarOtpMobileRegister.visibility = View.GONE
                    }






                }
                binding.btnEditImageRegister.setOnClickListener(){

                    val pictureDialog = AlertDialog.Builder(this)
                    pictureDialog.setTitle("select Action")

                    val pictureDialogItem= arrayOf("select from Capture","select from gallery")

                    pictureDialog.setItems(pictureDialogItem){
                        dialog,which->
                        when(which){
                            0 ->{imageChooser()}
                            1->{imageCupture()}
                        }
                    }
                    pictureDialog.show()



        }

    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
            Toast.makeText(this, "it.longitude.toFloat().toString()", Toast.LENGTH_LONG).show()
            return

        }

        val location = mFusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {

                sharedPreferences.edit().putFloat("longitude", it.longitude.toFloat()).apply()
                sharedPreferences.edit().putFloat("latitude", it.latitude.toFloat()).apply()

            }


        }


    }


    private fun fisrtNameFocusListener() {

        binding.editFirstNameRegister.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {

                val nameText = binding.editFirstNameRegister.text.toString()
                if (!isValidateName(nameText)){
                binding.inputFirstNameRegister.helperText = "Must be Character"
                }
                else{
                    binding.inputFirstNameRegister.helperText = null
                }

            }

        }
    }
    private fun lastNameFocusListener()  {


        binding.editLastNameRegister.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                val nameText = binding.editLastNameRegister.text.toString()
                if (!isValidateName(nameText)) {
                    binding.inputLastNameRegister.helperText = "Must be Character"
                } else {
                    binding.inputLastNameRegister.helperText = null
                }
            }
        }
    }

    private fun emailFocusListener() {

        binding.editEmailRegister.setOnFocusChangeListener { _, hasFocus ->
            val emailText = binding.editEmailRegister.text.toString()
            if(!hasFocus){
                if (!isValidEmail(emailText)) {
                    binding.inputEmailRegister.helperText = "Invalid Email Address"
                }
            }
            else{
                binding.inputEmailRegister.helperText = null
            }

            }

        }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex =
            Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,}$", RegexOption.IGNORE_CASE)
        return emailRegex.matches(email)
    }
    private fun isValidateName(name: String): Boolean {
        val regex = Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$")
        return regex.matches(name)
    }

    private fun passwordFocusListener() {
        binding.editPasswordRegister.setOnFocusChangeListener { v, hasFocus ->


            if(!hasFocus){
                val passwordText = binding.editPasswordRegister.text.toString()
                binding.inputPasswordRegister.helperText = validPassword(passwordText)

            }
        }
    }

    private fun validPassword(passwordText:String): CharSequence? {

        if(passwordText.length < 8){
            return "minimum 8 character password"
        }
//        if(!passwordText.matches(".*[A_Z].*".toRegex())){
//            return "Must Contain 1 Upper-case Character"
//        }
        return null
    }
    private fun phoneFocusListener() {

        binding.editPhoneNumberRegister.setOnFocusChangeListener { _, hasFocus ->

            if(!hasFocus){
                val phoneNumber = binding.editPhoneNumberRegister.text.toString()

                if(isValidPhoneNumber(phoneNumber)){
                         binding.inputPhoneNumberRegister.helperText = "Invalid phone number"
                }
                else{
                    binding.inputPhoneNumberRegister.helperText =null
                }
            }
        }
    }
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val regex = Regex("^\\+[1-9]\\d{1,14}\$")
        return regex.matches(phoneNumber)
    }
    @SuppressLint("SuspiciousIndentation", "CommitPrefEdits")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
//                Profile.REQUEST_CODE_IMAGE-> {
//                    selectedImageUri = data?.data
//                    binding.IVRegester.setImageURI(null)
//
//                    binding.IVRegester.setImageURI(selectedImageUri)
//                    editor.putString("selectedImageUri",selectedImageUri.toString())
//                    editor.apply()
//
//                }
                Profile.REQUEST_CODE_IMAGE_BITMAP->{
                     bmp = data?.extras?.get("data") as Bitmap
                    binding.IVRegester.setImageBitmap(bmp)
                    sharedPreferences.edit().putString("bmp",bmp.toString())
                    sharedPreferences.edit().apply()
                }


            }

        }
    }

    private fun uploadImage() {
        TODO("Not yet implemented")
    }


    private fun imageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        resultImage.launch(intent)
    }
    private fun imageCupture(){
        val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,Profile.REQUEST_CODE_IMAGE_BITMAP)
    }

    @SuppressLint("Recycle")
    private fun   getImagePart(): MultipartBody.Part {
        val filesDir=applicationContext.filesDir

        val file= File(filesDir,"image.png",)
        val inputStream = contentResolver.openInputStream(selectedImageUri)
        val outputSteam = FileOutputStream(file)
        inputStream!!.copyTo(outputSteam)

        val requestBody = file.asRequestBody("image/*".toMediaType())
        val part = MultipartBody.Part.createFormData("Profile",file.name,requestBody)

        return part

    }



}


