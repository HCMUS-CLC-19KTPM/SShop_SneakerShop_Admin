package com.example.sshop_sneakershop_admin.Order.controllers

import com.example.sshop_sneakershop_admin.Order.models.Order

interface IOrderController {
    suspend fun getAllOrders(): ArrayList<Order>
    suspend fun getOrderById(id: String): Order
    fun updateOrder(id: String, status: String)
    fun onGetAllOrders()
    fun onGetOrderById(id: String)
}