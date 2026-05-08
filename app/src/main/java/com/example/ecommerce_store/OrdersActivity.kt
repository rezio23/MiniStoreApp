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

class OrdersActivity : BaseActivity() {

    private lateinit var rvOrders: RecyclerView
    private lateinit var tvEmptyOrders: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        rvOrders = findViewById(R.id.rvOrders)
        tvEmptyOrders = findViewById(R.id.tvEmptyOrders)

        btnBack.setOnClickListener { finish() }

        setupRecyclerView()
        refreshOrders()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupBottomNavigation(bottomNav, R.id.nav_profile)
    }

    override fun onResume() {
        super.onResume()
        refreshOrders()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        updateCartBadge(bottomNav)
    }

    private fun setupRecyclerView() {
        rvOrders.layoutManager = LinearLayoutManager(this)
        rvOrders.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
    }

    private fun refreshOrders() {
        val orders = CartManager.getInstance(this).getOrders()
        rvOrders.adapter = OrderAdapter(orders)

        if (orders.isEmpty()) {
            tvEmptyOrders.visibility = TextView.VISIBLE
            rvOrders.visibility = RecyclerView.GONE
        } else {
            tvEmptyOrders.visibility = TextView.GONE
            rvOrders.visibility = RecyclerView.VISIBLE
        }
    }
}
