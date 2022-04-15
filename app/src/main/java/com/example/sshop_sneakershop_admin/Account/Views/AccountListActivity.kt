package com.example.sshop_sneakershop_admin.Account.Views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.R
import com.google.android.material.appbar.MaterialToolbar


class AccountListActivity : AppCompatActivity() {
    private lateinit var accounts : ArrayList<Account>

    private lateinit var topAppBar: MaterialToolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_account_list)

        val accountRecyclerView = findViewById<RecyclerView>(R.id.account_list_recycler_view)
        topAppBar = findViewById(R.id.account_list_toolbar)

        val account = Account(
            "1",
            "Nguyễn Văn A",
            "0123456789",
            "absds",
            "absds",
            "absds",
            "absds",
            "absds",
        true)
        accounts = listOf(
            account,
            account,
            account,
            account,
            account,
            account,
            account,
            account
        ).toCollection(ArrayList())

        val adapter = AccountAdapter(accounts)

        accountRecyclerView.adapter = adapter

        accountRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)

        topAppBar.setNavigationOnClickListener {
            finish()
        }
    }
}