package com.example.sshop_sneakershop_admin.Order

import com.example.sshop_sneakershop_admin.Order.models.Order
import com.example.sshop_sneakershop_admin.Order.models.OrderModel

class OrderService {
    private val orderModel: OrderModel = OrderModel()
    suspend fun getAllOrders(): ArrayList<Order> {
        return orderModel.getAllOrders()
    }
    suspend fun getOrderById(id: String): Order {
        return orderModel.getOrderById(id)
    }
    fun updateOrder(orderId: String, status: String): Boolean {
        return orderModel.updateOrder(orderId, status)
    }
}