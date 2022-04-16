package com.example.sshop_sneakershop_admin.Auth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sshop_sneakershop_admin.Auth.controllers.AuthController
import com.example.sshop_sneakershop_admin.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity(), IAuthView {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var authController: AuthController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authController = AuthController(this)

        binding.forgotPasswordInputEmail.setText("sshopsneaker@gmail.com")

        binding.forgotButtonBackToSignin.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        binding.forgotPasswordButtonSend.setOnClickListener {
            authController.onForgotPassword(binding.forgotPasswordInputEmail.text.toString())
        }
    }

    override fun onLoginSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLoginFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgotPasswordSuccess(message: String) {
        Log.d("ForgotPasswordActivity", message)
        AlertDialog.Builder(this)
            .setTitle("Forgot Password")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
            .show()
    }

    override fun onForgotPasswordFailed(message: String) {
        Log.d("ForgotPasswordActivity", message)
        AlertDialog.Builder(this)
            .setTitle("Forgot Password")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}