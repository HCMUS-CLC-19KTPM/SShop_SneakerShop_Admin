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
    suspend fun getAllUsers(): ArrayList<Account>{
        var accounts = ArrayList<Account>()
        try{
            db.collection("account").get().await().documents.forEach{
                val account = it.toObject(Account::class.java)
                accounts.add(account!!)
            }
        }catch (exception: Exception){
            exception.printStackTrace()
        }
        accounts.removeIf { it.email == "sshopsneaker@gmail.com" }
        return accounts
    }
    suspend fun getUserById(id: String): Account{
        var account = Account()
        try{
            db.collection("account").whereEqualTo("id", id).get().await()
                .documents.forEach{
                    account = it.toObject(Account::class.java)!!
                }
        }catch (exception: Exception){
            exception.printStackTrace()
        }
        return account
    }

    /**
     * update name, email, address, phone, gender, birthday
     * update fullName, email, address, phone, gender, dob
     */
    suspend fun updateUser(account: Account): Boolean{
        try{
            db.collection("account").whereEqualTo("id", account.id).get().await()
                .documents.forEach{
                    it.reference.update("fullName", account.fullName,
                        "email", account.email, "address", account.address,
                        "phone", account.phone, "gender", account.gender, "dob", account.dob).await()
                }
        }catch (exception: Exception){
            exception.printStackTrace()
            return false
        }
        return true
    }
    suspend fun deleteAccount(id: String){
        try{
            db.collection("account").whereEqualTo("id", id).get().await()
                .documents.forEach{
                    it.reference.delete().await()
                }
        }catch (exception: Exception){
            exception.printStackTrace()
        }
    }

}