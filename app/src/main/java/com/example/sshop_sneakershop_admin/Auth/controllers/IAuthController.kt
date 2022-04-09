package com.example.sshop_sneakershop_admin.Auth.controllers


import com.google.firebase.auth.AuthCredential

interface IAuthController {
    fun onSignIn(email: String, password: String)
    fun onSiginInWithGoogle(credential: AuthCredential)
    fun onSignOut()
}