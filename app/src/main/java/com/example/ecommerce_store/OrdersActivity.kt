package com.example.ecommerce_store

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrdersActivity : AppCompatActivity() {

    private lateinit var rvOrders: RecyclerView
    private lateinit var tvEmptyOrders: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        rvOrders = findViewById(R.id.rvOrders)
        tvEmptyOrders = findViewById(R.id.tvEmptyOrders)

        btnBack.setOnClickListener { finish() }

        setupRecyclerView()
        refreshOrders()
    }

    private fun setupRecyclerView() {
        rvOrders.layoutManager = LinearLayoutManager(this)
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