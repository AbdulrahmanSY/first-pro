package com.example.maptask.presentationLayer.userInterface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.maptask.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var email:String?=null
    private var password:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextLogin.setOnClickListener()
        {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        binding.tvCreateAccount.setOnClickListener(){
            startActivity(Intent(this,Register::class.java))
        }

        emailFocusListener()
        passwordFocusListener()

        if (isValidEmail(binding.editEmailLogin.text.toString())&& validPassword(binding.editPasswordLogin.text.toString())==null){

            email = binding.editEmailLogin.text.toString()
            password =binding.editPasswordLogin.text.toString()





        }





    }


    private fun passwordFocusListener() {
        binding.editPasswordLogin.setOnFocusChangeListener { v, hasFocus ->


            if(!hasFocus){
                val passwordText = binding.editPasswordLogin.text.toString()
                binding.inputPasswordLogin.helperText = validPassword(passwordText)

            }
        }
    }
    private fun validPassword(passwordText:String): String? {

        if(passwordText.length < 8){
            return "minimum 8 character password"
        }
//        if(!passwordText.matches(".*[A_Z].*".toRegex())){
//            return "Must Contain 1 Upper-case Character"
//        }
        return null
    }

    private fun emailFocusListener() {

        binding.inputEmailLogin.setOnFocusChangeListener { _, hasFocus ->
            val emailText = binding.editEmailLogin.text.toString()
            if(!hasFocus){
                if (!isValidEmail(emailText)) {
                    binding.inputEmailLogin.helperText = "Invalid Email Address"
                }
            }
            else{
                binding.inputEmailLogin.helperText = null
            }

        }

    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex =
            Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,}$", RegexOption.IGNORE_CASE)
        return emailRegex.matches(email)
    }
}