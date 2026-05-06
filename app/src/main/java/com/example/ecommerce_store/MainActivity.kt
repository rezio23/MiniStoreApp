package com.example.ecommerce_store

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var btnMenu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupDrawerNavigation()
        setupBottomNavigation()
        setupRecyclerViews()
    }

    private fun initViews() {
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        btnMenu = findViewById(R.id.btnMenu)

        // Open drawer on burger icon click
        btnMenu.setOnClickListener {
            drawerLayout.open()
        }
    }

    private fun setupDrawerNavigation() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_men -> {
                    showToast("Selected: Men")
                }
                R.id.menu_women -> {
                    showToast("Selected: Women")
                }
                R.id.menu_kids -> {
                    showToast("Selected: Kids")
                }
                R.id.menu_sports -> {
                    showToast("Selected: Sports")
                }
                R.id.menu_perfume -> {
                    showToast("Selected: Perfume")
                }
                R.id.menu_accessories -> {
                    showToast("Selected: Accessories")
                }
                R.id.menu_orders -> {
                    showToast("My Orders")
                }
                R.id.menu_wishlist -> {
                    showToast("Wishlist")
                }
                R.id.menu_settings -> {
                    showToast("Settings")
                }
            }
            drawerLayout.close()
            true
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.nav_stores -> {
                    startActivity(Intent(this, StoreActivity::class.java))
                    true
                }
                R.id.nav_cart -> {
                    showToast("Cart")
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Select Home by default
        bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun setupRecyclerViews() {
        // Featured Products
        val rvFeatured = findViewById<RecyclerView>(R.id.rvFeatured)
        rvFeatured.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFeatured.adapter = ProductAdapter(getFeaturedProducts())

        // New Arrivals
        val rvNewArrivals = findViewById<RecyclerView>(R.id.rvNewArrivals)
        rvNewArrivals.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvNewArrivals.adapter = ProductAdapter(getNewArrivals())
    }

    private fun getFeaturedProducts(): List<Product> {
        return listOf(
            Product(1, "Prada Leather Bag", 299.99, 399.99, R.drawable.product_prada),
            Product(2, "Smart Gadget Set", 49.99, 79.99, R.drawable.product_gadget),
            Product(3, "Designer Handbag", 89.99, 129.99, R.drawable.product_bag),
            Product(4, "Luxury Perfume", 79.99, 99.99, R.drawable.product_perfume),
            Product(5, "Sports Jacket", 59.99, 89.99, R.drawable.product_jacket)
        )
    }

    private fun getNewArrivals(): List<Product> {
        return listOf(
            Product(6, "Prada Leather Bag", 299.99, 399.99, R.drawable.product_prada),
            Product(7, "Designer Handbag", 89.99, 129.99, R.drawable.product_bag),
            Product(8, "Luxury Perfume", 79.99, 99.99, R.drawable.product_perfume),
            Product(9, "Sports Jacket", 59.99, 89.99, R.drawable.product_jacket),
            Product(10, "Smart Gadget Set", 49.99, 79.99, R.drawable.product_gadget)
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isOpen) {
            drawerLayout.close()
        } else {
            super.onBackPressed()
        }
    }
}
