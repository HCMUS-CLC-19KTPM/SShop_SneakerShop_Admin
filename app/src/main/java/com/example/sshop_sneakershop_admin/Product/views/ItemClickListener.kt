package com.example.sshop_sneakershop_admin.Product.views

import com.example.sshop_sneakershop_admin.Product.models.Product

interface ItemClickListener {
    fun onClick(product: Product)
}