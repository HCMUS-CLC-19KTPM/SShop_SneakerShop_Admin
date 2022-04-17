package com.example.sshop_sneakershop_admin.Product.Views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Product.controllers.ProductController
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityProductListBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductListAcitivity : AppCompatActivity(),
    IProductView, ItemClickListener {
    private var products: ArrayList<Product> = ArrayList()
    private lateinit var binding: ActivityProductListBinding
    private lateinit var productController: ProductController

    override fun onStart() {
        super.onStart()
        val auth = Firebase.auth
        if (auth.currentUser == null || !auth.currentUser!!.isEmailVerified) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productController = ProductController(this)

        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        getAllProducts()
//        productRecyclerView = findViewById<RecyclerView>(R.id.product_list_recycler_view)

        val productListActivity = this
        binding.productListRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = ProductAdapter(products, productListActivity)
        }
        productController.onGetAllProducts()
        binding.productListToolbar.setNavigationOnClickListener {
            finish()
        }
        binding.productListButtonAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllProducts() {
        GlobalScope.launch(Dispatchers.Main) {
            products = productController.getAllProducts()
            binding.productListRecyclerView.adapter?.notifyDataSetChanged()
            Log.i("products 2", "${products.size}")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onShowAllProducts(products: ArrayList<Product>) {
//        this.products.clear()
        this.products.addAll(products)
        binding.productListRecyclerView.adapter?.notifyDataSetChanged()
        Log.i("result view", "${products.size}")
    }

    override fun onShowProductDetail(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onShowProductsByCategory(products: ArrayList<Product>) {
        TODO("Not yet implemented")
    }

    override fun onShowError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetailActivity::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }

}