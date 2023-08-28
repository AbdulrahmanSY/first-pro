package com.example.maptask.presentationLayer.userInterface
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.maptask.R
import com.example.maptask.databinding.ActivityOtpBinding
import com.example.maptask.presentationLayer.viewModel.UserViewModel
import com.goodiebag.pinview.Pinview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class OTP : AppCompatActivity() {
    lateinit var binding:ActivityOtpBinding
    lateinit var viewModel: UserViewModel
    lateinit private var type:String
    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
//viewModel
        viewModel = ViewModelProvider.AndroidViewModelFactory(this.application).create(UserViewModel::class.java)
        binding.btnBackArrOtp.setOnClickListener(){
            finish()
        }


        type = intent.getStringExtra("typ").toString()
        val to = intent.getStringExtra("to").toString()
        val  resend_cooldown = intent.getIntExtra("resend_cooldown",10).toLong()
        val email:String = intent.getStringExtra("email").toString()

            countDown(resend_cooldown *1000)

            binding.otpPinview.setCursorColor(R.color.black)
            binding.otpPinview.setPinViewEventListener(object : Pinview.PinViewEventListener {
            override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {

                when (type) {
                    "REGISTER" -> {
                                            binding.proOtp.visibility = View.VISIBLE
                                            lifecycleScope.launch(Dispatchers.IO) {
                                                viewModel.check(" ",type, email, to, pinview?.value.toString())
                                            }
                                            viewModel.checkLive.observe(this@OTP , androidx.lifecycle.Observer {

                                                Toast.makeText(this@OTP,to,Toast.LENGTH_LONG).show()
                                                binding.proOtp.visibility = View.GONE
                                                if(it.isSuccessful){
                                                    val token = it.body()!!.token
                                //                            Toast.makeText(this@OTP,token.toString(),Toast.LENGTH_LONG).show()
                                                    binding.imRightOtp.visibility = View.VISIBLE

                                                    val data = Intent().apply {
                                                        putExtra("token", token)
                                                    }
                                                    setResult(Activity.RESULT_OK, data).toString()
                                                    finish()
                                                } else if (it.code()==403){
                                                    binding.tvErrorOtp.text = it.message().toString()
                                                    binding.imErrorOtp.visibility = View.VISIBLE

                                                } else if (it.code()==422){
                                                    binding.tvErrorOtp.text = it.message().toString()
                                                    binding.imErrorOtp.visibility = View.VISIBLE

                                                } else
                                                    finish()
                                            })
                                            binding.imErrorOtp.visibility = View.GONE
                                            binding.imRightOtp.visibility = View.GONE
                                            binding.tvErrorOtp.text = " "
                    }
                    "UPDATE_EMAIL" -> {
                                            Toast.makeText(this@OTP,"UPDATE_EMAIL Type",Toast.LENGTH_LONG).show()

                                            binding.proOtp.visibility = View.VISIBLE
                                            lifecycleScope.launch(Dispatchers.IO) {
                                                viewModel.check(intent.getStringExtra("typ").toString(),type, email, to, pinview?.value.toString())
                                            }
                                            viewModel.checkLive.observe(this@OTP , androidx.lifecycle.Observer {

                                                Toast.makeText(this@OTP,to,Toast.LENGTH_LONG).show()
                                                binding.proOtp.visibility = View.GONE
                                                if(it.isSuccessful){
                                                    val token = it.body()!!.token
                                                    Toast.makeText(this@OTP,"token email : ${token.toString()}",Toast.LENGTH_LONG).show()
                                                    binding.imRightOtp.visibility = View.VISIBLE

                                                    val data = Intent().apply {
                                                        putExtra("token_email", token)
                                                    }
                                                    setResult(Activity.RESULT_OK, data).toString()
                                                    finish()
                                                } else if (it.code()==403){
                                                    binding.tvErrorOtp.text = it.message().toString()
                                                    binding.imErrorOtp.visibility = View.VISIBLE

                                                } else if (it.code()==422){
                                                    binding.tvErrorOtp.text = it.message().toString()
                                                    binding.imErrorOtp.visibility = View.VISIBLE

                                                } else
                                                    finish()
                                            })
                                            binding.imErrorOtp.visibility = View.GONE
                                            binding.imRightOtp.visibility = View.GONE
                                            binding.tvErrorOtp.text = " "

                    }
                    else -> Toast.makeText(this@OTP,"error Type",Toast.LENGTH_LONG).show()
                }

            }
        })

    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Toast.makeText(this@OTP,"back presss",Toast.LENGTH_LONG).show()
            finish()
        return super.onKeyDown(keyCode, event)
    }
    private fun countDown(resend_cooldown:Long) {
        binding.btnResendOtp.visibility = View.GONE
        object : CountDownTimer(resend_cooldown, 1000) {

            // Callback function, fired on regular interval
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {


                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.tvCounterOtp.setText("resend after(" + min +":"+sec +")")
            }

            override fun onFinish() {
                binding.btnResendOtp.visibility = View.VISIBLE
            }
        }.start()


    }


}







