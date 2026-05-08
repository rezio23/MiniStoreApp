package com.example.ecommerce_store

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class WishlistActivity : BaseActivity() {

    private lateinit var rvWishlist: RecyclerView
    private lateinit var tvEmptyWishlist: LinearLayout
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

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupBottomNavigation(bottomNav, R.id.nav_profile)
    }

    override fun onResume() {
        super.onResume()
        refreshWishlist()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        updateCartBadge(bottomNav)
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
        rvWishlist.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
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
