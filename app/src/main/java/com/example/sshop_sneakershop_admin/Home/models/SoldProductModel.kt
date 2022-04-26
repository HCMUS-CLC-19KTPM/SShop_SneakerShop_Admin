package com.example.sshop_sneakershop_admin.Home.models

import com.example.sshop_sneakershop_admin.Product.models.Product
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId

class SoldProductModel {
    private var db = Firebase.firestore

    @Throws(Exception::class)
    suspend fun getAllSoldProducts(): ArrayList<Chart> {
        val soldProducts = ArrayList<Chart>()
        val map = HashMap<LocalDate, Double>()
        try {
            db.collection("sold_product").orderBy("createdAt", Query.Direction.ASCENDING)
                .get()
                .await()
                .documents.forEach {
                    val product = it.toObject(SoldProduct::class.java)
                    if (product != null) {
                        val localDate = product.createdAt.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()

                        if (map.containsKey(localDate)) {
                            map[localDate] = map[localDate]!! + product.total
                        } else {
                            map[localDate] = product.total
                        }
                    }
                }

            map.forEach {
                soldProducts.add(
                    Chart(it.key, it.value)
                )
            }
        } catch (e: Exception) {
            throw e
        }

        soldProducts.sortBy { it.createdAt }
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