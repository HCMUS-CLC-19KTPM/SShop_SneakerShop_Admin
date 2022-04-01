package com.example.sshop_sneakershop_admin.Product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.Views.ProductAdapter
import com.example.sshop_sneakershop_admin.R

class ProductListAcitivity : AppCompatActivity() {
    lateinit var products: ArrayList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val productRecyclerView = findViewById<RecyclerView>(R.id.admin_product_list_recycler_view)

        val product = Product("123", 1000.0,"Nike Jordan", "jordan.jpg", "Long description", 10);
        products = listOf(
            product,
            product,
            product,
            product,
            product,
            product,
            product,
            product
        ).toCollection(ArrayList())

        val adapter = ProductAdapter(products)

        productRecyclerView.adapter = adapter
        productRecyclerView.layoutManager = LinearLayoutManager(this) //GridLayoutManager

    }
}