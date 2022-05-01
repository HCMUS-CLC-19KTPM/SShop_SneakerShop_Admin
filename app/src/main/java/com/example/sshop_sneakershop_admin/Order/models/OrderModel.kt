package com.example.sshop_sneakershop_admin.Order.models

import android.util.Log
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class OrderModel {
    private var db = Firebase.firestore

    suspend fun getAllOrders(): ArrayList<Order> {
        val orders = ArrayList<Order>()
        try {
            db.collection("order")
                .orderBy("startDate", Query.Direction.DESCENDING)
                .get()
                .await()
                .documents.forEach {
                    val order = it.toObject(Order::class.java)
                    orders.add(order!!)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return orders
    }

    suspend fun getOrderById(id: String): Order {
        var order = Order()
        var products = ArrayList<Product>()
        var product = Product()
        try {
            db.collection("order").document(id).get().await()
                .toObject(Order::class.java)
                .let {
                    order = it!!
                }
            // name, description, price, image, quantity
            for (item in order.cart) {
                db.collection("product").document(item.id).get().await()
                    .toObject(Product::class.java)
                    .let {
                        product = it!!
                        val quantity = order.cart.find { it.id == product.id }!!.quantity
                        val price = order.cart.find { it.id == item.id }!!.price
                        Log.i("cart-value", "$quantity - $price")
                        product.quantity = order.cart.find { it.id == item.id }!!.quantity
                        product.price = order.cart.find { it.id == item.id }!!.price
                        products.add(product)
                    }
            }
            order.cart.clear()
            order.cart.addAll(products)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return order
    }

    fun updateOrder(orderID: String, status: String): Boolean {
        try {
            db.collection("order").document(orderID)
                .update("deliveryStatus", status)
                .addOnSuccessListener {
                    Log.i("updateOrder", "success")
                }
                .addOnFailureListener {
                    Log.i("updateOrder", "fail")
                }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
}