package com.example.sshop_sneakershop_admin.Account.controllers

import com.example.sshop_sneakershop_admin.Account.models.Account

interface IAccountController {
    suspend fun getAllUsers(): ArrayList<Account>
    suspend fun getUserById(id: String): Account
    suspend fun updateUser(account: Account)
    suspend fun deleteUser(id: String): Boolean
    fun onGetAllUsers()
    fun onGetUserById(id: String)
    fun onUpdateUser(account: Account)
    fun onDeleteUser(id: String)
}