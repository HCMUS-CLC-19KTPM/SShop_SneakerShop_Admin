package com.example.sshop_sneakershop_admin.Account.views

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop_admin.Account.controllers.AccountController
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityAccountDetailBinding


class AccountDetailActivity : AppCompatActivity(), IAccountView {
    private lateinit var binding: ActivityAccountDetailBinding
    private lateinit var accountController: AccountController
    private var account: Account = Account()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        accountController = AccountController(this)

        setSupportActionBar(binding.accountDetailToolbar)
        binding.accountDetailToolbar.setNavigationOnClickListener {
            finish()
        }

    }

    override fun onShowAllUsers(users: ArrayList<Account>) {
        TODO("Not yet implemented")
    }

    override fun onShowUser(user: Account) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteUserSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteUserFailed(message: String) {
        TODO("Not yet implemented")
    }
}

