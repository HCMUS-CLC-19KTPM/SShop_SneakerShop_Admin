package com.example.sshop_sneakershop_admin.Auth.controllers

import com.example.sshop_sneakershop_admin.Auth.AuthService
import com.example.sshop_sneakershop_admin.Auth.views.IAuthView
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthController(
    private var view: IAuthView
) : IAuthController {
    private lateinit var authService: AuthService

    init {
        authService = AuthService()
    }
    /**
     * sign in with email and password
     */
    override fun onSignIn(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            var result = authService.signIn(email, password)
            if (result) {
                view.onLoginSuccess("Login Success")
            } else {
                view.onLoginFailed("Login failed. Invalid email or password or account not activated please check your email")
            }
        }
    }

    override fun onSiginInWithGoogle(credential: AuthCredential) {
        TODO("Not yet implemented")
    }

    override fun onSignOut() {
        TODO("Not yet implemented")
    }
}