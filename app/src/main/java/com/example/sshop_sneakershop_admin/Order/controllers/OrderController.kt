package com.example.sshop_sneakershop_admin.Order.controllers

import com.example.sshop_sneakershop_admin.Order.OrderService
import com.example.sshop_sneakershop_admin.Order.models.Order
import com.example.sshop_sneakershop_admin.Order.views.IOrderView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderController(private val view: IOrderView): IOrderController {
    private val orderService: OrderService = OrderService()
    override suspend fun getAllOrders(): ArrayList<Order> {
        return orderService.getAllOrders()
    }

    override suspend fun getOrderById(id: String): Order {
        return orderService.getOrderById(id)
    }

    override fun updateOrder(id: String, status: String): Boolean {
        return orderService.updateOrder(id, status)
    }

    override fun onGetAllOrders() {
        CoroutineScope(Dispatchers.IO).launch {
            val listOrder = orderService.getAllOrders()
            withContext(Dispatchers.Main){
                view.onShowAllOrder(listOrder)
            }
        }
    }

    override fun onGetOrderById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val order = orderService.getOrderById(id)
            withContext(Dispatchers.Main){
                view.onShowOrderDetail(order)
            }
        }
    }

}
