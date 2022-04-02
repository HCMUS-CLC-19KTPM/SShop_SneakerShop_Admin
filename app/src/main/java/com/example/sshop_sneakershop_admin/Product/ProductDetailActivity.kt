package com.example.sshop_sneakershop_admin.Product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.sshop_sneakershop_admin.R

class ProductDetailActivity : AppCompatActivity() {
    var editPencil : ImageView?=null
    var submitBtn: Button?=null
    var productNameET: EditText?=null
    var productPriceET: EditText?=null
    var productDescET: EditText?=null
    var productInfoET: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        editPencil = findViewById(R.id.product_detail_imageview_edit_pencil)
        submitBtn = findViewById(R.id.product_detail_button_submit)
        productNameET = findViewById(R.id.product_detail_edittext_product_name)
        productPriceET = findViewById(R.id.product_detail_edittext_product_old_price)
        productDescET = findViewById(R.id.product_detail_edittext_description_content)
        productInfoET = findViewById(R.id.product_detail_edittext_info_content)

        editPencil?.setOnClickListener {
            turnOnEditMode()
        }
        submitBtn?.setOnClickListener {
            turnOfEditMode()
        }
    }

    fun turnOnEditMode(){
        productNameET!!.isFocusableInTouchMode = true
        productPriceET!!.isFocusableInTouchMode = true
        productDescET!!.isFocusableInTouchMode = true
        productInfoET!!.isFocusableInTouchMode = true

        productNameET!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        productPriceET!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        productDescET!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        productInfoET!!.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material)
        submitBtn!!.isEnabled = true
    }
    fun turnOfEditMode(){
        productNameET!!.clearFocus()
        productPriceET!!.clearFocus()
        productDescET!!.clearFocus()
        productInfoET!!.clearFocus()

        productNameET!!.isFocusableInTouchMode = false
        productPriceET!!.isFocusableInTouchMode = false
        productDescET!!.isFocusableInTouchMode = false
        productInfoET!!.isFocusableInTouchMode = false

        productNameET!!.background = null
        productPriceET!!.background = null
        productDescET!!.background = null
        productInfoET!!.background = null

        submitBtn!!.isEnabled = false
    }
}