package com.example.ecommerce_store

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WishlistActivity : AppCompatActivity() {

    private lateinit var rvWishlist: RecyclerView
    private lateinit var tvEmptyWishlist: TextView
    private lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        rvWishlist = findViewById(R.id.rvWishlist)
        tvEmptyWishlist = findViewById(R.id.tvEmptyWishlist)

        btnBack.setOnClickListener { finish() }

        setupRecyclerView()
        refreshWishlist()
    }

    private fun setupRecyclerView() {
        rvWishlist.layoutManager = LinearLayoutManager(this)
        wishlistAdapter = WishlistAdapter(
            items = emptyList(),
            onRemove = { productId ->
                CartManager.getInstance(this).removeFromWishlist(productId)
                refreshWishlist()
                Toast.makeText(this, "Removed from wishlist", Toast.LENGTH_SHORT).show()
            },
            onAddToCart = { item ->
                CartManager.getInstance(this).addToCart(
                    Product(item.productId, item.name, item.price, null, item.imageRes)
                )
            }
        )
        rvWishlist.adapter = wishlistAdapter
    }

    private fun refreshWishlist() {
        val items = CartManager.getInstance(this).getWishlist()
        wishlistAdapter.updateList(items)

        if (items.isEmpty()) {
            tvEmptyWishlist.visibility = TextView.VISIBLE
            rvWishlist.visibility = RecyclerView.GONE
        } else {
            tvEmptyWishlist.visibility = TextView.GONE
            rvWishlist.visibility = RecyclerView.VISIBLE
        }
    }
}