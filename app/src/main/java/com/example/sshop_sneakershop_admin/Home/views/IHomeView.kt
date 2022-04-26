package com.example.sshop_sneakershop_admin.Home.views

import com.example.sshop_sneakershop_admin.Product.models.Product

interface IHomeView {
    fun onShowTop10Products(products: ArrayList<Product>)
}