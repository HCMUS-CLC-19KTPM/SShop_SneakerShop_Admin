package com.example.sshop_sneakershop_admin.Product.models

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProductModel {
    private var db = Firebase.firestore
    private var storageRef = Firebase.storage.reference

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
    fun deleteProduct(id: String):Boolean{
        try{
            db.collection("product").document(id).delete()
                .addOnSuccessListener {
                    Log.i("delete", "Document successfully deleted!")
                }
                .addOnFailureListener{
                    Log.i("delete", "Error deleting document", it)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
    fun addProduct(product: Product):Boolean{
        try{
            db.collection("product")
                .add(product)
                .addOnSuccessListener {
                    Log.i("create", "Document successfully uploaded!")
                }
                .addOnFailureListener{
                    Log.i("create", "Error uploading document", it)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
    fun uploadImage(imageUri: Uri): String{
        val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formater.format(now)
        var url = ""
        try{
            storageRef.child("product/${fileName}.jpg").putFile(imageUri)
                .addOnSuccessListener {
                    Log.i("create", "Document successfully created!")
                    storageRef.child("product/${fileName}.jpg").downloadUrl.addOnSuccessListener {
                        url = it.toString()
                    }
                }
                .addOnFailureListener{
                    Log.i("create", "Error creating document", it)
                }

        }catch (e: Exception){
            e.printStackTrace()
            return ""
        }
        return url
    }

}