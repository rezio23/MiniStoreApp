package com.example.ecommerce_store

data class StoreProduct(
    val id: Int,
    val name: String,
    val price: Double,
    val oldPrice: Double? = null,
    val imageRes: Int,
    val category: String,
    val description: String = ""
)
