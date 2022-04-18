package com.example.sshop_sneakershop_admin.Product.models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProductModel {
    private var db = Firebase.firestore

    suspend fun getAllProducts(): ArrayList<Product> {
        val products = ArrayList<Product>()
        try{
            db.collection("product").get().await()
                .documents.forEach {
                    val product = it.toObject(Product::class.java)
                    products.add(product!!)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return products
    }
    suspend fun getProductById(id: String): Product{
        var product = Product()
        try{
            db.collection("product").document(id).get().await()
                .toObject(Product::class.java)
                .let {
                    product = it!!
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return product
    }
    suspend fun getProductsByCategory(category: String): ArrayList<Product>{
        val products = ArrayList<Product>()
        try{
            db.collection("product").whereEqualTo("category", category).get().await()
                .documents.forEach {
                    val product = it.toObject(Product::class.java)
                    products.add(product!!)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return products
    }
    /**
     * Update product on name, price, discount, category, description, quantity
     */
    fun updateProduct(product: Product):Boolean{
        try{
            db.collection("product").document(product.id)
                .update("name", product.name, "price", product.price, "discount", product.discount, "category", product.category, "description", product.description, "stock", product.stock)
                .addOnSuccessListener {
                    Log.i("update", "Document successfully updated!")
                }
                .addOnFailureListener{
                    Log.i("update", "Error updating document", it)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

}