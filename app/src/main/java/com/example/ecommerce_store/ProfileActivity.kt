package com.example.ecommerce_store

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnCart = findViewById<LinearLayout>(R.id.btnCart)
        val btnOrders = findViewById<LinearLayout>(R.id.btnOrders)
        val btnWishlist = findViewById<LinearLayout>(R.id.btnWishlist)

        btnBack.setOnClickListener {
            finish()
        }

        btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        btnOrders.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
        }

        btnWishlist.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupBottomNavigation(bottomNav, R.id.nav_profile)
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        updateCartBadge(bottomNav)
    }
}
