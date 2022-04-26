package com.example.sshop_sneakershop_admin.Home.models

import java.time.LocalDate
import java.util.Date

data class Chart(
    val createdAt: LocalDate,
    val total: Double
)

data class SoldProduct(
    val productId: String,
    val quantity: Int,
    val total: Double,
    val createdAt: Date
) {
    constructor() : this("", 0, 0.0, Date())
}