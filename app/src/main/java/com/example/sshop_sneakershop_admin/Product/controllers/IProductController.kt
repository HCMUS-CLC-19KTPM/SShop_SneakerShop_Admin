package com.example.sshop_sneakershop_admin.Product.controllers

import com.example.sshop_sneakershop_admin.Product.models.Product

interface IProductController {
    suspend fun getAllProducts(): ArrayList<Product>
    suspend fun getProductById(id: String): Product
    suspend fun getProductsByCategory(category: String): ArrayList<Product>
    fun createProduct(product: Product): Boolean
    fun updateProduct(product: Product): Boolean
    fun deleteProduct(id: Int): Boolean
    fun onGetAllProducts()
    fun onGetProductById(id: String)
    fun onGetProductByCategory(category: String)
}