package com.example.ecommerce_store

data class Order(
    val orderId: String,
    val items: List<CartItem>,
    val total: Double,
    val date: String,
    val itemCount: Int,
    val shippingName: String = "",
    val shippingPhone: String = "",
    val shippingAddress: String = "",
    val paymentMethod: String = "Cash on Delivery"
)