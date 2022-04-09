package com.example.sshop_sneakershop_admin.Auth

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthService {
    private val auth = Firebase.auth

    suspend fun signIn(email: String, password: String): Boolean{
        try{
            auth.signInWithEmailAndPassword(email, password).await()
            if(!auth.currentUser!!.isEmailVerified){
                return false
            }
            return true
        }catch(e: Exception){
            Log.e("AuthService", e.toString())
        }
        return false
    }

}