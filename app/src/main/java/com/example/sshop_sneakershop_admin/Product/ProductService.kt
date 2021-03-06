package com.example.sshop_sneakershop_admin.Product

import android.net.Uri
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.Product.models.ProductModel

class ProductService {
    private val productModel: ProductModel = ProductModel()

    suspend fun getAllProducts(): ArrayList<Product> {
        return productModel.getAllProducts()
    }
    suspend fun getTop10Products(): ArrayList<Product> {
        return productModel.getTop10Products()
    }
    suspend fun getProductById(id: String): Product {
        return productModel.getProductById(id)
    }
    suspend fun getProductsByCategory(category: String): ArrayList<Product> {
        return productModel.getProductsByCategory(category)
    }
    fun updateProduct(product: Product): Boolean{
        return productModel.updateProduct(product)
    }
    fun deleteProduct(id: String): Boolean{
        return productModel.deleteProduct(id)
    }
    suspend fun addProduct(product: Product): Product{
        return productModel.addProduct(product)
    }
    suspend fun uploadImage(uri: Uri): String{
        return productModel.uploadImage(uri)
    }
}