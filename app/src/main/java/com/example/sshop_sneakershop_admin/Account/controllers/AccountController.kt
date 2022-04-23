package com.example.sshop_sneakershop_admin.Account.controllers

import com.example.sshop_sneakershop_admin.Account.AccountService
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.Account.views.IAccountView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountController(
    private val view: IAccountView
): IAccountController {
    private val accountService: AccountService = AccountService()
    override suspend fun getAllUsers(): ArrayList<Account> {
        return accountService.getAllUsers()
    }

    override suspend fun getUserById(id: String): Account {
        return accountService.getUserById(id)
    }

    override suspend fun updateUser(account: Account) {
        val result = accountService.updateUser(account)
        if(result){
            view.onUpdateUserSuccess("Update user success!")
        }else{
            view.onUpdateUserFailed("Update user failed!")
        }
    }

    override fun onGetAllUsers(){
        CoroutineScope(Dispatchers.IO).launch {
            val user = accountService.getAllUsers()
            withContext(Dispatchers.Main){
                view.onShowAllUsers(user)
            }
        }
    }

    override fun onGetUserById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = accountService.getUserById(id)
            withContext(Dispatchers.Main){
                view.onShowUser(user)
            }
        }
    }

    override fun onUpdateUser(account: Account) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = accountService.updateUser(account)
            withContext(Dispatchers.Main){
                if(result){
                    view.onUpdateUserSuccess("Update user's information success!")
                }else{
                    view.onUpdateUserFailed("Update user's information failed!")
                }
            }
        }
    }


}