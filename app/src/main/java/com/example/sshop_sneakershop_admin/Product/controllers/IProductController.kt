package com.example.sshop_sneakershop_admin.Product.controllers

import android.net.Uri
import com.example.sshop_sneakershop_admin.Product.models.Product

interface IProductController {
    suspend fun getAllProducts(): ArrayList<Product>
    suspend fun getProductById(id: String): Product
    suspend fun getProductsByCategory(category: String): ArrayList<Product>
    fun addProduct(product: Product)
    fun uploadImage(uri: Uri)
    fun updateProduct(product: Product): Boolean
    fun deleteProduct(id: String): Boolean
    fun onGetAllProducts()
    fun onGetProductById(id: String)
    fun onGetProductByCategory(category: String)
}