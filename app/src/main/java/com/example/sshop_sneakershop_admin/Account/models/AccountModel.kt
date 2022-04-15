package com.example.sshop_sneakershop_admin.Account.models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AccountModel {
    private var db = Firebase.firestore
//    suspend fun getUsers() {
//        val docRef = db.collection("account").get().await()
//        Log.i("abc", "${docRef.documents}")
//    }

    suspend fun getUser(email: String) : Account?{
        var account: Account ?= null
        try{
            db.collection("account").whereEqualTo("email", email).get().await()
                .documents.forEach{
                    account = it.toObject(Account::class.java)
                }
        }catch (exception: Exception){
            exception.printStackTrace()
        }
        Log.i("abc", "account = ${account}")
        return account
    }

    suspend fun insertUser(account: Account){
        try{
            db.collection("account").document().set(account).await()
//            db.collection("account").add(account).await()
        }catch (exception: Exception){
            exception.printStackTrace()
        }
    }



}