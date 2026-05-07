package com.example.ecommerce_store

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartManager private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    private var _cart = mutableListOf<CartItem>()
    private var _wishlist = mutableListOf<WishlistItem>()
    private var _orders = mutableListOf<Order>()

    init {
        loadCart()
        loadWishlist()
        loadOrders()
    }

    // ---- Cart ----
    fun getCartItems(): List<CartItem> = _cart.toList()
    fun getCartCount(): Int = _cart.sumOf { it.quantity }
    fun getCartTotal(): Double = _cart.sumOf { it.total }

    fun addToCart(product: Product) {
        val existing = _cart.find { it.productId == product.id }
        if (existing != null) {
            existing.quantity++
        } else {
            _cart.add(CartItem(product.id, product.name, product.price, product.imageRes))
        }
        saveCart()
    }

    fun addToCart(product: StoreProduct) {
        val existing = _cart.find { it.productId == product.id }
        if (existing != null) {
            existing.quantity++
        } else {
            _cart.add(CartItem(product.id, product.name, product.price, product.imageRes))
        }
        saveCart()
    }

    fun removeFromCart(productId: Int) {
        _cart.removeAll { it.productId == productId }
        saveCart()
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        val item = _cart.find { it.productId == productId }
        if (item != null) {
            if (quantity <= 0) {
                _cart.remove(item)
            } else {
                item.quantity = quantity
            }
            saveCart()
        }
    }

    fun clearCart() {
        _cart.clear()
        saveCart()
    }

    private fun saveCart() {
        prefs.edit().putString(KEY_CART, gson.toJson(_cart)).apply()
    }

    private fun loadCart() {
        val json = prefs.getString(KEY_CART, null) ?: return
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        _cart = gson.fromJson(json, type) ?: mutableListOf()
    }

    // ---- Wishlist ----
    fun getWishlist(): List<WishlistItem> = _wishlist.toList()

    fun isInWishlist(productId: Int): Boolean = _wishlist.any { it.productId == productId }

    fun toggleWishlist(product: Product) {
        if (isInWishlist(product.id)) {
            _wishlist.removeAll { it.productId == product.id }
        } else {
            _wishlist.add(WishlistItem(product.id, product.name, product.price, product.imageRes))
        }
        saveWishlist()
    }

    fun toggleWishlist(product: StoreProduct) {
        if (isInWishlist(product.id)) {
            _wishlist.removeAll { it.productId == product.id }
        } else {
            _wishlist.add(WishlistItem(product.id, product.name, product.price, product.imageRes))
        }
        saveWishlist()
    }

    fun removeFromWishlist(productId: Int) {
        _wishlist.removeAll { it.productId == productId }
        saveWishlist()
    }

    private fun saveWishlist() {
        prefs.edit().putString(KEY_WISHLIST, gson.toJson(_wishlist)).apply()
    }

    private fun loadWishlist() {
        val json = prefs.getString(KEY_WISHLIST, null) ?: return
        val type = object : TypeToken<MutableList<WishlistItem>>() {}.type
        _wishlist = gson.fromJson(json, type) ?: mutableListOf()
    }

    // ---- Orders ----
    fun getOrders(): List<Order> = _orders.toList()

    fun placeOrder(): Order? {
        if (_cart.isEmpty()) return null
        val order = Order(
            orderId = "ORD-${System.currentTimeMillis()}",
            items = _cart.toList(),
            total = getCartTotal(),
            date = java.text.SimpleDateFormat("dd MMM yyyy, HH:mm", java.util.Locale.getDefault())
                .format(java.util.Date()),
            itemCount = getCartCount()
        )
        _orders.add(0, order)
        saveOrders()
        clearCart()
        return order
    }

    private fun saveOrders() {
        prefs.edit().putString(KEY_ORDERS, gson.toJson(_orders)).apply()
    }

    private fun loadOrders() {
        val json = prefs.getString(KEY_ORDERS, null) ?: return
        val type = object : TypeToken<MutableList<Order>>() {}.type
        _orders = gson.fromJson(json, type) ?: mutableListOf()
    }

    companion object {
        private const val PREFS_NAME = "ecommerce_prefs"
        private const val KEY_CART = "cart_items"
        private const val KEY_WISHLIST = "wishlist_items"
        private const val KEY_ORDERS = "orders"

        @Volatile
        private var instance: CartManager? = null

        fun getInstance(context: Context): CartManager {
            return instance ?: synchronized(this) {
                instance ?: CartManager(context.applicationContext).also { instance = it }
            }
        }
    }
}