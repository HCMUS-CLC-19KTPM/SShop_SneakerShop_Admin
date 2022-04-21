package com.example.sshop_sneakershop_admin.Order.views

import com.example.sshop_sneakershop_admin.Order.models.Order

interface IOrderView {
    fun onShowAllOrder(orders: ArrayList<Order>)
    fun onShowOrderDetail(order: Order)
    fun onUpdateOrderSuccess(message: String)
    fun onUpdateOrderFailed(message: String)
}