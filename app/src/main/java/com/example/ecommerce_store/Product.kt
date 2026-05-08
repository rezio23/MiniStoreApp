package com.example.ecommerce_store

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val oldPrice: Double? = null,
    val imageRes: Int = R.drawable.ic_store,
    val description: String = ""
)
