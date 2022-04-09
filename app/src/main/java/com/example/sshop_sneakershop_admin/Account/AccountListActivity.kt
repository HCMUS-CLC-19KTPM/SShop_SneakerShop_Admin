package com.example.sshop_sneakershop_admin.Account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Account.Views.AccountAdapter
import com.example.sshop_sneakershop_admin.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar


class AccountListActivity : Fragment() {
    private lateinit var accounts : ArrayList<Account>

    private lateinit var topAppBar: MaterialToolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_account_list)
//
//        val accountRecyclerView = findViewById<RecyclerView>(R.id.admin_account_list_recycler_view)
//        topAppBar = findViewById(R.id.admin_account_list_toolbar)
//
//        val account = Account(
//            "1234nsnnnd32312",
//            "Nguyễn Văn A",
//            "123456789",
//            "dsaj@#mdsa",
//            true,
//            "nguyenvana123@gmail.com"
//        )
//        accounts = listOf(
//            account,
//            account,
//            account,
//            account,
//            account,
//            account,
//            account,
//            account
//        ).toCollection(ArrayList())
//
//        val adapter = AccountAdapter(accounts)
//
//        accountRecyclerView.adapter = adapter
//
//        accountRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
//
//        topAppBar.setNavigationOnClickListener {
//            finish()
//        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_account_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accountRecyclerView = view.findViewById<RecyclerView>(R.id.admin_account_list_recycler_view)

        val account = Account(
            "1234nsnnnd32312",
            "Nguyễn Văn A",
            "123456789",
            "dsaj@#mdsa",
            true,
            "nguyenvana123@gmail.com"
        )
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

        accountRecyclerView.layoutManager = LinearLayoutManager(context) //GridLayoutManager(this, 2)


    }
}