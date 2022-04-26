package com.example.sshop_sneakershop_admin.Product.models

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
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
    suspend fun getTop10Products(): ArrayList<Product>{
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
        return products.slice(0..9) as ArrayList<Product>
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
    suspend fun addProduct(product: Product):Product{
        try{
            val id = db.collection("product").add(product).await().id
            db.collection("product").document(id).update("id", id)
            product.id = id
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return product
    }
    suspend fun uploadImage(imageUri: Uri):String{
        val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formater.format(now)
        var url = ""
        try{
            storageRef.child("product/${fileName}.jpg").putFile(imageUri).await()
            val uri = storageRef.child("product/${fileName}.jpg").downloadUrl.await()
            url = uri.toString()
            Log.i("image-url","1: ${url}")
            return url
        }catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }

}