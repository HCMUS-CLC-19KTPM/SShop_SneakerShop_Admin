package com.example.sshop_sneakershop_admin.Account

import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.Account.models.AccountModel

class AccountService {
    private val accountModel: AccountModel = AccountModel()

    suspend fun getAllUsers(): ArrayList<Account>{
        return accountModel.getAllUsers()
    }
    suspend fun getUserById(id: String): Account{
        return accountModel.getUserById(id)
    }
    suspend fun updateUser(account: Account): Boolean{
        return accountModel.updateUser(account)
    }
    suspend fun deleteUser(id: String): Boolean{
        return accountModel.deleteAccount(id)
    }
}