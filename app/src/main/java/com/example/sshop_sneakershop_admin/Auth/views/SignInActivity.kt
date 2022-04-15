package com.example.sshop_sneakershop_admin.Auth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sshop_sneakershop_admin.Auth.controllers.AuthController
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity(), IAuthView {

    private lateinit var binding: ActivitySignInBinding


    private lateinit var controller: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = AuthController(this)

        binding.signinInputEmail.setText("admin@gmail.com")
        binding.signinInputPassword.setText("admin")

        binding.signinTextviewForgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.signinButtonLogin.setOnClickListener {
            Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLoginSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLoginFailed(message: String) {
        TODO("Not yet implemented")
    }
}