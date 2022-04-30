package com.example.sshop_sneakershop_admin.Home.models

import java.util.Date

data class ChartEntry(
    val brand: String,
    val quantity: Int
)

data class SoldProduct(
    val brand: String,
    val productId: String,
    val quantity: Int,
    val total: Double,
    val createdAt: Date
) {
    constructor() : this(
        "", "", 0, 0.0, Date()
    )
}