package com.example.sshop_sneakershop_admin.Account.views

import android.content.Intent
import android.graphics.Color.red
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop_admin.Account.controllers.AccountController
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityAccountDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso


class AccountDetailActivity : AppCompatActivity(), IAccountView {
    private lateinit var binding: ActivityAccountDetailBinding
    private lateinit var accountController: AccountController
    private var user: Account = Account()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        accountController = AccountController(this)

        val accountID = intent.getStringExtra("item-id").toString()
        accountController.onGetUserById(accountID)
        toggleAccountDetail()


        binding.deleteAccountBtn.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure to delete this account?")
                .setPositiveButton("Yes") { dialog, which ->
                    accountController.onDeleteUser(accountID)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
        setSupportActionBar(binding.accountDetailToolbar)
        binding.accountDetailToolbar.setNavigationOnClickListener {
            finish()
        }

    }
    /**
     * Create account's profile info
     */
    private fun toggleAccountDetail() {
        binding.profileLinearLayoutAccDetail.setOnClickListener {
            if (binding.gridLayout.visibility == View.GONE) {
                binding.gridLayout.visibility = View.VISIBLE
                binding.profileButtonEditInfo.visibility = View.VISIBLE
            } else {
                binding.gridLayout.visibility = View.GONE
                binding.profileButtonEditInfo.visibility = View.GONE
            }
        }

        binding.profileButtonShowAccDetail.setOnClickListener {
            if (binding.gridLayout.visibility == View.GONE) {
                binding.gridLayout.visibility = View.VISIBLE
                binding.profileButtonEditInfo.visibility = View.VISIBLE
            } else {
                binding.gridLayout.visibility = View.GONE
                binding.profileButtonEditInfo.visibility = View.GONE
            }
        }

        binding.profileButtonEditInfo.setOnClickListener {
            val intent = Intent(this, EditAccountActivity::class.java)
            intent.putExtra("item-id", user.id)
            startActivity(intent)
        }
    }
    override fun onShowAllUsers(users: ArrayList<Account>) {
        TODO("Not yet implemented")
    }

    override fun onShowUser(user: Account) {
        this.user = user
        binding.profileTextValueName.text = user.fullName
        binding.profileTextValueEmail.text = user.email
        binding.profileTextValuePhone.text = user.phone
        binding.profileTextValueAddress.text = user.address
        binding.profileTextValueGender.text = user.gender
        binding.profileTextValueDob.text = user.dob
        if (user.status){
            binding.profileTextValueStatus.text = "Active"
            binding.profileTextValueStatus.setTextColor(ContextCompat.getColor(this, R.color.forest_green))
        }
        else{
            binding.profileTextValueStatus.text = "Banned"
            binding.profileTextValueStatus.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        if(TextUtils.isEmpty(user.avatar)) {
            binding.profileImageAvatar.setImageResource(R.drawable.account_avatar)
        } else {
            Picasso.get().load(user.avatar).into(binding.profileImageAvatar)
        }
    }

    override fun onUpdateUserSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateUserFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteUserSuccess(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Success")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    override fun onDeleteUserFailed(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Failed")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        val accountID = intent.getStringExtra("item-id").toString()
        accountController.onGetUserById(accountID)
    }
}

