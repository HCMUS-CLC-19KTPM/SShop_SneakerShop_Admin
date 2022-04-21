package com.example.sshop_sneakershop_admin.Order.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R

class OrderDetailActivity : AppCompatActivity() {
    lateinit var products: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
    }
}