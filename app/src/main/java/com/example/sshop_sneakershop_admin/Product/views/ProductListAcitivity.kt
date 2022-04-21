package com.example.sshop_sneakershop_admin.Product.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sshop_sneakershop_admin.Auth.views.SignInActivity
import com.example.sshop_sneakershop_admin.Product.controllers.ProductController
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ActivityProductListBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductListAcitivity : AppCompatActivity(),
    IProductView, ItemClickListener{
    private var products: ArrayList<Product> = ArrayList()
    private lateinit var binding: ActivityProductListBinding
    private lateinit var productController: ProductController
    private lateinit var categoryAdapter: ArrayAdapter<String>
    private lateinit var productAdapter: ProductAdapter
    private var fullProductList: ArrayList<Product> = ArrayList()
    var previousSelection = 0

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

        val productListActivity = this

//        binding.productListRecyclerView.apply {
//            layoutManager =
//                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
//            adapter = ProductAdapter(products, productListActivity, fullProductList)
//        }
        binding.productListRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        productAdapter = ProductAdapter(products, productListActivity, fullProductList)
        binding.productListRecyclerView.adapter = productAdapter


        productController.onGetAllProducts()

        categoryAdapter = ArrayAdapter(this, R.layout.spinner_item, resources.getStringArray(R.array.category))
        binding.productListSpinnerCategory.adapter = categoryAdapter
        binding.productListSpinnerCategory.setSelection(0)

        binding.productListSpinnerCategory.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                previousSelection = position
                productController.onGetProductByCategory(categoryAdapter.getItem(position).toString())
            }
        }
        setSupportActionBar(binding.productListToolbar)
        binding.productListToolbar.setNavigationOnClickListener {
            finish()
        }
        binding.productListButtonAddProduct.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        menuInflater.inflate(R.menu.top_app_bar_with_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        Log.i("searchView", "go here 1")
        val searchView = searchItem.actionView as SearchView
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("searchView", "go here 2")
                productAdapter.getFilter().filter(newText)
                return false
            }
        })
        return true
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
        this.products.clear()
        this.fullProductList.clear()
        this.products.addAll(products)
        this.fullProductList.addAll(products)
        binding.productListRecyclerView.adapter?.notifyDataSetChanged()
        Log.i("result view", "${products.size}")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onShowProductsByCategory(products: ArrayList<Product>) {
        this.products.clear()
        this.products.addAll(products)
        this.fullProductList.clear()
        this.fullProductList.addAll(products)
        binding.productListRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onClick(product: Product) {
        val intent = Intent(applicationContext, ProductDetailActivity::class.java)
        intent.putExtra("item-id", product.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if(categoryAdapter.getItem(previousSelection) == "All") {
            productController.onGetAllProducts()
        } else {
            productController.onGetProductByCategory(categoryAdapter.getItem(previousSelection).toString())
        }
        binding.productListToolbar.collapseActionView()
    }

    override fun onShowProductDetail(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onShowMessage(msg: String) {
        TODO("Not yet implemented")
    }

    override fun onAddProductSuccess(product: Product) {
        TODO("Not yet implemented")
    }

    override fun onUploadImageSuccess(url: String) {
        TODO("Not yet implemented")
    }
}