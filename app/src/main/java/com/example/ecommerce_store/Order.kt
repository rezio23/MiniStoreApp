package com.example.ecommerce_store

data class Order(
    val orderId: String,
    val items: List<CartItem>,
    val total: Double,
    val date: String,
    val itemCount: Int
)