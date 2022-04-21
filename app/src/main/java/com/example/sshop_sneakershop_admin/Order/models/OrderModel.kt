package com.example.sshop_sneakershop_admin.Order.models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class OrderModel {
    private var db = Firebase.firestore

    suspend fun getAllOrders(): ArrayList<Order>{
        val orders = ArrayList<Order>()
        try{
            db.collection("order").get().await()
                .documents.forEach{
                    val order = it.toObject(Order::class.java)
                    orders.add(order!!)
                }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return orders
    }
    suspend fun getOrderById(id: String): Order{
        var order = Order()
        try{
            db.collection("order").document(id).get().await()
                .toObject(Order::class.java)
                .let {
                    order = it!!
                }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return order
    }
    fun updateOrder(orderID: String, status: String): Boolean{
        try{
            db.collection("order").document(orderID)
                .update("status", status)
                .addOnSuccessListener {
                    Log.i("updateOrder", "success")
                }
                .addOnFailureListener{
                    Log.i("updateOrder", "fail")
                }
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
        return true
    }
}