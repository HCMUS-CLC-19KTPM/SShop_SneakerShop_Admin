package com.example.sshop_sneakershop_admin.Account.Views

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sshop_sneakershop_admin.R


class AccountDetailActivity : AppCompatActivity() {

    private var username: EditText? = null
    private var address: EditText? = null
    private var phone: EditText? = null
    private var gender: EditText? = null
    private var birthday: EditText? = null
    private var status: Spinner? = null
    private var deleteBtn: Button? = null
    private var submitBtn: Button? = null
    private var editProfileTV: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)

        username = findViewById(R.id.account_detail_edittext_username_2)
        address = findViewById(R.id.account_detail_edittext_address)
        phone = findViewById(R.id.account_detail_edittext_phone)
        gender = findViewById(R.id.account_detail_edittext_gender)
        birthday = findViewById(R.id.account_detail_edittext_birthday)
        status = findViewById(R.id.account_detail_spinner_status)
        deleteBtn = findViewById(R.id.account_detail_button_delete)
        submitBtn = findViewById(R.id.account_detail_button_confirm)
        editProfileTV = findViewById(R.id.account_detail_textview_edit_profile)

        editProfileTV!!.setOnClickListener {
            turnOnEditMode()
        }
        submitBtn!!.setOnClickListener {
            turnOfEditMode()

        }

        deleteBtn!!.setOnClickListener {
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }
    fun turnOnEditMode(){
        username!!.isFocusableInTouchMode = true
        address!!.isFocusableInTouchMode = true
        phone!!.isFocusableInTouchMode = true
        gender!!.isFocusableInTouchMode = true
        birthday!!.isFocusableInTouchMode = true

        username!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        address!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        phone!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        gender!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        birthday!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)


        editProfileTV!!.text = "Editing Mode"
    }
    fun turnOfEditMode(){
        username!!.clearFocus()
        address!!.clearFocus()
        phone!!.clearFocus()
        gender!!.clearFocus()
        birthday!!.clearFocus()

        username!!.isFocusableInTouchMode = false
        address!!.isFocusableInTouchMode = false
        phone!!.isFocusableInTouchMode = false
        gender!!.isFocusableInTouchMode = false
        birthday!!.isFocusableInTouchMode = false

        username!!.background = null
        address!!.background = null
        phone!!.background = null
        gender!!.background = null
        birthday!!.background = null

        editProfileTV!!.text = "Edit Profile"
    }
}

