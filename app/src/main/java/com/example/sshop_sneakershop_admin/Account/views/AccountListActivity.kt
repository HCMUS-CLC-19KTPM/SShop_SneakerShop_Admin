package com.example.sshop_sneakershop_admin.Account.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Account.controllers.AccountController
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityAccountListBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AccountListActivity : AppCompatActivity(), IAccountView, AccountClickListener {
    private lateinit var binding: ActivityAccountListBinding
    private lateinit var accountController: AccountController
    private var users: ArrayList<Account> = ArrayList()
    private lateinit var accountAdapter: AccountAdapter
    private var fullUserList: ArrayList<Account> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        accountController = AccountController(this)
        val accountListActivity = this
        binding.accountListRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        accountAdapter = AccountAdapter(users, accountListActivity, fullUserList)
        binding.accountListRecyclerView.adapter = accountAdapter

        accountController.onGetAllUsers()
        setSupportActionBar(binding.accountListToolbar)
        binding.accountListToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_with_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                accountAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }
    override fun onClick(account: Account) {
        val intent = Intent(applicationContext, AccountDetailActivity::class.java)
        intent.putExtra("item-id", account.id)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onShowAllUsers(users: ArrayList<Account>) {
        this.users.clear()
        this.fullUserList.clear()
        this.users.addAll(users)
        this.fullUserList.addAll(users)
        binding.accountListRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onShowUser(user: Account) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserSuccess(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Update User")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    override fun onUpdateUserFailed(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Update User")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDeleteUserSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteUserFailed(message: String) {
        TODO("Not yet implemented")
    }
}