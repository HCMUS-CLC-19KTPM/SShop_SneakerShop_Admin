package com.example.sshop_sneakershop_admin.Auth.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.sshop_sneakershop_admin.Auth.controllers.AuthController
import com.example.sshop_sneakershop_admin.R

class SignInActivity : AppCompatActivity(), IAuthView {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signInWithGoogleButton: Button

    private lateinit var controller: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        controller = AuthController(this)

        emailEditText = findViewById(R.id.signin_input_email)
        passwordEditText = findViewById(R.id.signin_input_password)
        signInButton = findViewById(R.id.signin_button_login)
        signInWithGoogleButton = findViewById(R.id.signin_button_register_with_google)

        signInButton.setOnClickListener {

        }
    }

    override fun onLoginSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLoginFailed(message: String) {
        TODO("Not yet implemented")
    }
}