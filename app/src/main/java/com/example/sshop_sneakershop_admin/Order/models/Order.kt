package com.example.sshop_sneakershop_admin.Order.models

import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.Product.models.Product
import java.util.*
import kotlin.collections.ArrayList

data class Order(
    var id: String,
    var name: String,
    var phone: String,
    var address: String,
    var cart: ArrayList<Product>,
    var deliveryStatus: String,
    var orderedDate: Date,
    var startDate: Date,
    var endDate: Date,
    var shippingFee: Double,
    var totalCost: Double,
    var userId: String
) {
    constructor() : this("", "", "", "", 0.0, Date(), Date())


    constructor(
        id: String,
        name: String,
        address: String,
        phone: String,
        totalCost: Double,
        startDate: Date,
        endDate: Date
    ) : this(
        id, name, phone, address,
        ArrayList(), "", Date(), startDate, endDate, 0.0, totalCost, ""
    )
    constructor(order: Order) : this() {
        this.id = order.id
        this.name = order.name
        this.phone = order.phone
        this.address = order.address
        this.cart = order.cart
        this.deliveryStatus = order.deliveryStatus
        this.orderedDate = order.orderedDate
        this.startDate = order.startDate
        this.endDate = order.endDate
        this.shippingFee = order.shippingFee
        this.totalCost = order.totalCost
        this.userId = order.userId
    }
}