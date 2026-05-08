package com.example.ecommerce_store

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class CartActivity : BaseActivity() {

    private lateinit var rvCart: RecyclerView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnCheckout: MaterialButton
    private lateinit var tvEmptyCart: LinearLayout
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        rvCart = findViewById(R.id.rvCart)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        btnCheckout = findViewById(R.id.btnCheckout)
        tvEmptyCart = findViewById(R.id.tvEmptyCart)

        btnBack.setOnClickListener { finish() }
        btnCheckout.setOnClickListener { checkout() }

        setupRecyclerView()
        refreshCart()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupBottomNavigation(bottomNav, R.id.nav_cart)
    }

    override fun onResume() {
        super.onResume()
        refreshCart()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        updateCartBadge(bottomNav)
    }

    private fun setupRecyclerView() {
        rvCart.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(
            items = emptyList(),
            onQuantityChanged = { productId, quantity ->
                CartManager.getInstance(this).updateQuantity(productId, quantity)
                refreshCart()
            },
            onRemove = { productId ->
                CartManager.getInstance(this).removeFromCart(productId)
                refreshCart()
            }
        )
        rvCart.adapter = cartAdapter
        rvCart.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
    }

    private fun refreshCart() {
        val cartItems = CartManager.getInstance(this).getCartItems()
        val total = CartManager.getInstance(this).getCartTotal()

        cartAdapter = CartAdapter(
            items = cartItems,
            onQuantityChanged = { productId, quantity ->
                CartManager.getInstance(this).updateQuantity(productId, quantity)
                refreshCart()
            },
            onRemove = { productId ->
                CartManager.getInstance(this).removeFromCart(productId)
                refreshCart()
            }
        )
        rvCart.adapter = cartAdapter

        tvTotalPrice.text = "$${String.format("%.2f", total)}"

        if (cartItems.isEmpty()) {
            tvEmptyCart.visibility = TextView.VISIBLE
            rvCart.visibility = RecyclerView.GONE
            btnCheckout.isEnabled = false
            btnCheckout.alpha = 0.5f
        } else {
            tvEmptyCart.visibility = TextView.GONE
            rvCart.visibility = RecyclerView.VISIBLE
            btnCheckout.isEnabled = true
            btnCheckout.alpha = 1.0f
        }
    }

    private fun checkout() {
        startActivity(Intent(this, CheckoutActivity::class.java))
    }
}
