package com.example.sshop_sneakershop_admin.Account.views

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.sshop_sneakershop_admin.Account.controllers.AccountController
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityEditProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class EditAccountActivity : AppCompatActivity(), IAccountView {
    private lateinit var accountController: AccountController
    private var user: Account = Account()
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var genderAdapter: ArrayAdapter<String>
    private lateinit var statusAdapter: ArrayAdapter<String>
    private lateinit var picker: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        accountController = AccountController(this)
        val userId = intent.getStringExtra("item-id").toString()
        accountController.onGetUserById(userId)


        setUpInputField()
        binding.editProfileButtonSubmit.setOnClickListener {
            submitButtonHandler()
        }
        setSupportActionBar(binding.editProfileToolbar)
        binding.editProfileToolbar.setNavigationOnClickListener {
            finish()
        }

    }
    fun setUpInputField(){
        val genders = listOf("Male", "Female", "Other")
        genderAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, genders)
        (binding.editProfileTextInputETGender as? AutoCompleteTextView)?.setAdapter(genderAdapter)

        val items = listOf("Active", "Banned")
        statusAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, items)
        (binding.editProfileTextInputETStatus as? AutoCompleteTextView)?.setAdapter(statusAdapter)

        binding.editProfileTextInputETDob.setOnClickListener {
            picker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val year: Int = year
                    val month: Int = month + 1
                    val day: Int = dayOfMonth
                    val calendar = Calendar.getInstance()
                    calendar.set(year, month, day)
                    binding.editProfileTextInputETDob.setText("$day/$month/$year")

                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            picker.show()
            picker.datePicker.maxDate = System.currentTimeMillis()
        }
    }
    fun submitButtonHandler(){
        if(!isValidInput()){
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show()
            return
        }
        user.fullName = binding.editProfileTextInputETName.text.toString()
        user.email = binding.editProfileTextInputETEmail.text.toString()
        user.address = binding.editProfileTextInputETAddress.text.toString()
        user.phone = binding.editProfileTextInputETPhone.text.toString()
        user.gender = binding.editProfileTextInputETGender.text.toString()
        user.dob = binding.editProfileTextInputETDob.text.toString()
        val status = binding.editProfileTextInputETStatus.text.toString()
        user.status = status == "Active"
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirm")
            .setMessage("Are you sure you want to update this account?")
            .setPositiveButton("Yes") { dialog, which ->
                accountController.onUpdateUser(user)
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
    fun isValidInput(): Boolean{
        if(binding.editProfileTextInputETName.text.toString().isEmpty()){
            binding.editProfileTextInputETName.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.editProfileTextInputETEmail.text.toString().isEmpty()){
            binding.editProfileTextInputETEmail.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.editProfileTextInputETAddress.text.toString().isEmpty()){
            binding.editProfileTextInputETAddress.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.editProfileTextInputETPhone.text.toString().isEmpty()){
            binding.editProfileTextInputETPhone.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.editProfileTextInputETGender.text.toString().isEmpty()){
            binding.editProfileTextInputETGender.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.editProfileTextInputETDob.text.toString().isEmpty()){
            binding.editProfileTextInputETDob.error = getString(R.string.error_input_empty)
            return false
        }
        if(binding.editProfileTextInputETStatus.text.toString().isEmpty()){
            binding.editProfileTextInputETStatus.error = getString(R.string.error_input_empty)
            return false
        }
        return true
    }
    override fun onShowAllUsers(users: ArrayList<Account>) {
        TODO("Not yet implemented")
    }

    override fun onShowUser(user: Account) {
        this.user = user
        binding.editProfileTextInputETName.setText(user.fullName)
        binding.editProfileTextInputETEmail.setText(user.email)
        binding.editProfileTextInputETPhone.setText(user.phone)
        binding.editProfileTextInputETAddress.setText(user.address)
        binding.editProfileTextInputETGender.setText(user.gender)
        binding.editProfileTextInputETDob.setText(user.dob)
        if(user.status){
            binding.editProfileTextInputETStatus.setText("Active")
        }else{
            binding.editProfileTextInputETStatus.setText("Banned")
        }
        if(TextUtils.isEmpty(user.avatar)) {
            binding.editProfileImageAvatar.setImageResource(R.drawable.account_avatar)
        } else {
            Picasso.get().load(user.avatar).into(binding.editProfileImageAvatar)
        }
    }

    override fun onUpdateUserSuccess(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Update success")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    override fun onUpdateUserFailed(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Update failed")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
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