package com.example.sshop_sneakershop_admin.Home.views

import com.example.sshop_sneakershop_admin.Home.models.ChartEntry
import com.example.sshop_sneakershop_admin.Product.models.Product
import kotlin.collections.ArrayList

interface IHomeView {
    fun onGetStatisticsSuccess(soldProduct: ArrayList<ChartEntry>)
    fun onGetStatisticsFailed(message: String)

    fun onGetBestSellerSuccess(bestSeller: ArrayList<Product>)
    fun onGetBestSellerFailed(message: String)
}