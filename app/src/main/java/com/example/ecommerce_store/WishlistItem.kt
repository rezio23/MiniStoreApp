package com.example.ecommerce_store

data class WishlistItem(
    val productId: Int,
    val name: String,
    val price: Double,
    val imageRes: Int
)