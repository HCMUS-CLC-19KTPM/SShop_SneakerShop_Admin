package com.example.sshop_sneakershop_admin.Order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.Product.Views.ProductAdapter
import com.example.sshop_sneakershop_admin.R

class OrderDetailActivity : AppCompatActivity() {
    lateinit var products: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val productRecyclerView = findViewById<RecyclerView>(R.id.order_detail_recycler_view)

        val myProduct = Product()
        products = listOf(myProduct, myProduct, myProduct,myProduct, myProduct, myProduct,myProduct, myProduct, myProduct).toCollection(ArrayList())

        val adapter = ProductAdapter(products)

        productRecyclerView.adapter = adapter

        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
    }
}