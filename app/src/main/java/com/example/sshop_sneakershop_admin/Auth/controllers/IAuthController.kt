package com.example.sshop_sneakershop_admin.Auth.controllers


import com.google.firebase.auth.AuthCredential

interface IAuthController {
    fun onSignIn(email: String, password: String)
    fun onSignInWithGoogle(credential: AuthCredential)
    fun onForgotPassword(email: String)
    fun onSignOut()
}