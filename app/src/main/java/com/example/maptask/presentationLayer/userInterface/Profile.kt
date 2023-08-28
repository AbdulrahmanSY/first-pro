package com.example.maptask.presentationLayer.userInterface

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.MediaStore.*
import android.provider.MediaStore.Images.*
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import com.example.maptask.R
import com.example.maptask.databinding.ActivityProfileBinding
import com.example.maptask.presentationLayer.viewModel.UserViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Url

class Profile : AppCompatActivity() , View.OnClickListener{
    lateinit var binding: ActivityProfileBinding
    lateinit var viewModel: UserViewModel
    private var selectedImageUri: Uri? = null
    private  var id:Int =1
    private lateinit var firstName:EditText
    private lateinit var lastName:EditText
    private lateinit var email: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var ImageURL: Url
    lateinit var sharedPreferences: SharedPreferences
    companion object {
        const val REQUEST_CODE_IMAGE = 101
        const val REQUEST_CODE_IMAGE_BITMAP = 100
        const val REQUEST_CODE_BACK = 1000
    }

    @SuppressLint("IntentReset")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstName = binding.editFirstName
        lastName = binding.editLastName
        email = binding.editEmail
        phoneNumber = binding.editPhoneNumber



        binding.btnBackToMainFromProfile.setOnClickListener(this)
        binding.btnSaveProfile.setOnClickListener(this)
        binding.btnEditImageProfile.setOnClickListener(this)

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE)

        firstName.setText(sharedPreferences.getString("fname","fname"))
        lastName.setText(sharedPreferences.getString("lname","lname"))
        email.setText(sharedPreferences.getString("email","email"))
        phoneNumber.setText(sharedPreferences.getString("editPhoneNumberRegister","mobile"))

        Toast.makeText(this@Profile,sharedPreferences.getString("access_token","access_token"),Toast.LENGTH_LONG).show()
    }



    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_back_to_main_from_profile->{
                            Toast.makeText(this, "free_rides", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MapsActivity::class.java))
                             }
            R.id.btn_save_profile -> {
                            Toast.makeText(this, "free_rides", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MapsActivity::class.java))
                           }
            R.id.btn_edit_image_profile-> {
//
                            Toast.makeText(this, "edit", Toast.LENGTH_LONG).show()
                            }
        }

    }


}

