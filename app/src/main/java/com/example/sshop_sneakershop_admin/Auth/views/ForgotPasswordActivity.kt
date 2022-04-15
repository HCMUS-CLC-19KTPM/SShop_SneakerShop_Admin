package com.example.sshop_sneakershop_admin.Auth.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sshop_sneakershop_admin.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPasswordInputEmail.setText("sshopsneaker@gmail.com")

        binding.forgotButtonBackToSignin.setOnClickListener {
            finish()
        }
    }
}