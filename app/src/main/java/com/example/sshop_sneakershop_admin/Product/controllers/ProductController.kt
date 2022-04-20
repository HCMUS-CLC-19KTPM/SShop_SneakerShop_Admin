package com.example.sshop_sneakershop_admin.Product.controllers

import android.net.Uri
import com.example.sshop_sneakershop_admin.Product.ProductService
import com.example.sshop_sneakershop_admin.Product.views.IProductView
import com.example.sshop_sneakershop_admin.Product.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductController(private val view: IProductView) : IProductController {
    private var productService: ProductService = ProductService()
    override suspend fun getAllProducts(): ArrayList<Product> {
        return productService.getAllProducts()
    }

    override fun onGetAllProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            val products = productService.getAllProducts()
            withContext(Dispatchers.Main){
                view.onShowAllProducts(products)
            }
        }
    }

    override suspend fun getProductById(id: String): Product {
        return productService.getProductById(id)
    }
    override fun onGetProductById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val product = productService.getProductById(id)
            withContext(Dispatchers.Main){
                view.onShowProductDetail(product)
            }
        }
    }

    override suspend fun getProductsByCategory(category: String): ArrayList<Product> {
        if (category == "All"){
            return productService.getAllProducts()
        }
        return productService.getProductsByCategory(category)
    }

    override fun onGetProductByCategory(category: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val products = if (category == "All") productService.getAllProducts() else productService.getProductsByCategory(category)
            withContext(Dispatchers.Main){
                view.onShowProductsByCategory(products)
            }
        }
    }

    /**
     * create product with brand, name, price, discount, category, description, image, releaseDate, stock
     * origin, rating, review will be null for default
     */
    override fun addProduct(product: Product): Boolean {
        return productService.addProduct(product)
    }

    override fun uploadImage(uri: Uri): String {
        return productService.uploadImage(uri)
    }

    override fun updateProduct(product: Product): Boolean {
        return productService.updateProduct(product)
    }

    override fun deleteProduct(id: String): Boolean {
        return productService.deleteProduct(id)
    }






}