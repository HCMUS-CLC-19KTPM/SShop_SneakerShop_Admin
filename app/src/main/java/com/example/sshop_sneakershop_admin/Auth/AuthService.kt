package com.example.sshop_sneakershop_admin.Auth

import android.util.Log
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.Account.models.AccountModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthService {
    private val auth = Firebase.auth
    private val accountModel = AccountModel()
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

    suspend fun signInWithGoogle(credential: AuthCredential): Boolean{
        try{
            auth.signInWithCredential(credential).await()
//            accountModel.getUsers()
            val email: String? = auth.currentUser!!.email
            if (email != "sshopsneaker@gmail.com"){
                return false
            }
            if(accountModel.getUser(email.toString()) == null){
                accountModel.insertUser(Account(email))
            }
            Log.d("AuthService", "signInWithCredential:success")

            return true
        }catch(e: Exception){
            Log.e("AuthService", e.toString())
        }
        return false
    }
    fun signOut(){
        auth.signOut()
    }
}