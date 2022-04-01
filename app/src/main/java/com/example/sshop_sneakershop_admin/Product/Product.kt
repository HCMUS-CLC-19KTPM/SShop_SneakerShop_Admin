package com.example.sshop_sneakershop_admin.Product

class Product(
    var id: String,
    var price: Double,
    var name: String,
    var image: String?,
    var description: String?,
    var quantity: Int?
) {
    constructor(id: String, price: Double, name: String, image: Int) : this(
        id,
        price,
        name,
        null,
        null,
        null
    ) {
        this.image = image.toString()
    }
}