package com.example.sshop_sneakershop_admin.Order.views

import com.example.sshop_sneakershop_admin.Order.models.Order

interface OrderClickListener {
    fun onClick(order: Order)
}