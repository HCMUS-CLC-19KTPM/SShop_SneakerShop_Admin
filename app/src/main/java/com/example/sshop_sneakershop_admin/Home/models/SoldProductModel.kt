package com.example.sshop_sneakershop_admin.Home.models

import com.example.sshop_sneakershop_admin.Product.models.Product
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SoldProductModel {
    private var db = Firebase.firestore

    @Throws(Exception::class)
    suspend fun getAllSoldProducts(): ArrayList<ChartEntry> {
        val soldProducts = ArrayList<ChartEntry>()
        val map = HashMap<String, Int>()
        try {
            db.collection("sold_product").orderBy("quantity", Query.Direction.DESCENDING)
                .get()
                .await()
                .documents.forEach {
                    val product = it.toObject(SoldProduct::class.java)
                    if (product != null) {
                        val brand = product.brand

                        if (map.containsKey(brand)) {
                            map[brand] = map[brand]!! + product.quantity
                        } else if (map.containsKey("Others")) {
                            map["Others"] = map["Others"]!! + product.quantity
                        }
                        else {
                            if (map.size < 3) {
                                map[brand] = product.quantity
                            } else {
                                map["Others"] = product.quantity
                            }
                        }
                    }
                }

            map.forEach {
                soldProducts.add(
                    ChartEntry(it.key, it.value)
                )
            }
        } catch (e: Exception) {
            throw e
        }

        soldProducts.sortByDescending { it.quantity }
        return soldProducts
    }

    /**
     * Get top 10 best seller products
     *
     * @return ArrayList<Product>
     */
    @Throws(Exception::class)
    suspend fun getBestSeller(): ArrayList<Product> {
        val bestSeller = ArrayList<Product>()
        try {
            db.collection("sold_product").orderBy("total", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .await()
                .documents.forEach {
                    val productId = it.data!!["productId"].toString()

                    val product = db.collection("product").document(productId)
                        .get().await().toObject(Product::class.java)
                    if (product != null) {
                        bestSeller.add(product)
                    }
                }
        } catch (e: Exception) {
            throw e
        }

        return bestSeller
    }
}