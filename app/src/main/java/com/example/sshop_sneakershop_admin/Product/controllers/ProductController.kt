package com.example.sshop_sneakershop_admin.Product.controllers

import android.net.Uri
import com.example.sshop_sneakershop_admin.Home.views.IHomeView
import com.example.sshop_sneakershop_admin.Product.ProductService
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.Product.views.IProductView
import kotlinx.coroutines.*

class ProductController(
    private val iProductView: IProductView ?= null,
    private val iHomeView: IHomeView?= null
) : IProductController {
    private var productService: ProductService = ProductService()
    override suspend fun getAllProducts(): ArrayList<Product> {
        return productService.getAllProducts()
    }

    override suspend fun getTop10Products(): ArrayList<Product> {
        return productService.getTop10Products()
    }

    override fun onGetAllProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            val products = productService.getAllProducts()
            withContext(Dispatchers.Main) {
                iProductView?.onShowAllProducts(products)
            }
        }
    }

    override fun onGetTop10Products() {
        CoroutineScope(Dispatchers.IO).launch {
            val products = productService.getTop10Products()
            withContext(Dispatchers.Main) {
                iHomeView?.onShowTop10Products(products)
            }
        }
    }

    override suspend fun getProductById(id: String): Product {
        return productService.getProductById(id)
    }

    override fun onGetProductById(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val product = productService.getProductById(id)
            withContext(Dispatchers.Main) {
                iProductView?.onShowProductDetail(product)
            }
        }
    }

    override suspend fun getProductsByCategory(category: String): ArrayList<Product> {
        if (category == "All") {
            return productService.getAllProducts()
        }
        return productService.getProductsByCategory(category)
    }

    override fun onGetProductByCategory(category: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val products =
                if (category == "All") productService.getAllProducts() else productService.getProductsByCategory(
                    category
                )
            withContext(Dispatchers.Main) {
                iProductView?.onShowProductsByCategory(products)
            }
        }
    }

    /**
     * create product with brand, name, price, discount, category, description, image, releaseDate, stock
     * origin, rating, review will be null for default
     */
    override fun addProduct(product: Product) {
        CoroutineScope(Dispatchers.Main).launch {
            val product = productService.addProduct(product)
            withContext(Dispatchers.Main) {
                iProductView?.onAddProductSuccess(product)
            }
        }
    }

    override fun uploadImage(uri: Uri) {
        CoroutineScope(Dispatchers.Main).launch {
            val image = productService.uploadImage(uri)
            withContext(Dispatchers.Main) {
                iProductView?.onUploadImageSuccess(image)
            }
        }
    }

    override fun updateProduct(product: Product): Boolean {
        return productService.updateProduct(product)
    }

    override fun deleteProduct(id: String): Boolean {
        return productService.deleteProduct(id)
    }


}