package com.example.sshop_sneakershop_admin.Home.views

import com.example.sshop_sneakershop_admin.Home.models.Chart
import com.example.sshop_sneakershop_admin.Home.models.SoldProduct
import com.example.sshop_sneakershop_admin.Product.models.Product
import java.util.Date
import kotlin.collections.ArrayList

interface IHomeView {
    fun onGetStatisticsSuccess(soldProduct: ArrayList<Chart>)
    fun onGetStatisticsFailed(message: String)

    fun onGetBestSellerSuccess(bestSeller: ArrayList<Product>)
    fun onGetBestSellerFailed(message: String)
}