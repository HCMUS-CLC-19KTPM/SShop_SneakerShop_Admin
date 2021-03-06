package com.example.sshop_sneakershop_admin.Product.views

import com.example.sshop_sneakershop_admin.Product.models.Product

interface IProductView {
    fun onShowAllProducts(products: ArrayList<Product>)
    fun onShowProductDetail(product: Product)
    fun onShowProductsByCategory(products: ArrayList<Product>)
    fun onShowMessage(msg: String)
    fun onAddProductSuccess(product: Product)
    fun onUploadImageSuccess(url: String)
}