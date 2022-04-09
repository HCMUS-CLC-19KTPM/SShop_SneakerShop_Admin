package com.example.sshop_sneakershop_admin.Auth.views

interface IAuthView {
    fun onLoginSuccess(message: String)
    fun onLoginFailed(message: String)
}