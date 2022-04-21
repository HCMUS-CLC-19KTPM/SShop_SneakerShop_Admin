package com.example.sshop_sneakershop_admin.Order.controllers

interface IOrderController {
    suspend fun getAllOrder(): ArrayList<Order>
}