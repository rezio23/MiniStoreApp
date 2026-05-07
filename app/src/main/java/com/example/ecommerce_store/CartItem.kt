package com.example.ecommerce_store

data class CartItem(
    val productId: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    var quantity: Int = 1
) {
    val total: Double get() = price * quantity
}