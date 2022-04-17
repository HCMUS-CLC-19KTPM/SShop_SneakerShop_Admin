package com.example.sshop_sneakershop_admin.Product.controllers

import com.example.sshop_sneakershop_admin.Product.ProductService
import com.example.sshop_sneakershop_admin.Product.Views.IProductView
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

    override fun createProduct(product: Product): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateProduct(product: Product): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteProduct(id: Int): Boolean {
        TODO("Not yet implemented")
    }






}