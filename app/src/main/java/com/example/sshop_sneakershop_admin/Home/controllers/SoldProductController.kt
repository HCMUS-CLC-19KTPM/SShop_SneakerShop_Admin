package com.example.sshop_sneakershop_admin.Home.controllers

import com.example.sshop_sneakershop_admin.Home.models.SoldProductModel
import com.example.sshop_sneakershop_admin.Home.views.IHomeView
import kotlinx.coroutines.*

class SoldProductController(private val activity: IHomeView? = null) : ISoldProductController {
    private val soldProductModel = SoldProductModel()


    @OptIn(DelicateCoroutinesApi::class)
    override fun onGetStatistics() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val soldProducts = soldProductModel.getAllSoldProducts()
                withContext(Dispatchers.Main) {
                    activity?.onGetStatisticsSuccess(soldProducts)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { activity?.onGetStatisticsFailed(it) }
                }
            }
        }
    }

    /**
     * Get top 10 best selling products
     */
    @OptIn(DelicateCoroutinesApi::class)
    override fun onGetBestSeller() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val soldProducts = soldProductModel.getBestSeller()

                withContext(Dispatchers.Main) {
                    activity?.onGetBestSellerSuccess(soldProducts)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { activity?.onGetBestSellerFailed(it) }
                }
            }
        }
    }
}