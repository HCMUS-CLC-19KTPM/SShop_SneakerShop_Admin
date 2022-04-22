package com.example.sshop_sneakershop_admin.Account.views

import com.example.sshop_sneakershop_admin.Account.models.Account

interface IAccountView {
    fun onShowAllUsers(users: ArrayList<Account>)
    fun onShowUser(user: Account)
    fun onUpdateUserSuccess(message: String)
    fun onUpdateUserFailed(message: String)
    fun onDeleteUserSuccess(message: String)
    fun onDeleteUserFailed(message: String)
}