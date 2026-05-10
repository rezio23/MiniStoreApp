package com.example.ecommerce_store

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity() {

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
        setupClickListeners()
        setupRecyclerViews()
        updateCartBadge()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isOpen) {
                    drawerLayout.close()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
        bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun initViews() {
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        btnMenu = findViewById(R.id.btnMenu)

        btnMenu.setOnClickListener {
            drawerLayout.open()
        }
    }

    private fun setupDrawerNavigation() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_men -> openStore("Men")
                R.id.menu_women -> openStore("Women")
                R.id.menu_kids -> openStore("Kids")
                R.id.menu_sports -> openStore("Sports")
                R.id.menu_perfume -> openStore("Perfume")
                R.id.menu_accessories -> openStore("Accessories")
                R.id.menu_orders -> startActivity(Intent(this, OrdersActivity::class.java))
                R.id.menu_wishlist -> startActivity(Intent(this, WishlistActivity::class.java))
                R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))
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
                    startActivity(Intent(this, CartActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
        bottomNavigation.selectedItemId = R.id.nav_home
    }

    private fun setupClickListeners() {
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.searchCard)?.setOnClickListener {
            openStore()
        }
        findViewById<TextView>(R.id.tvSeeAllFeatured)?.setOnClickListener {
            openStore()
        }
        findViewById<TextView>(R.id.tvSeeAllNew)?.setOnClickListener {
            openStore()
        }
        findViewById<com.google.android.material.button.MaterialButton>(R.id.btnShopNow)?.setOnClickListener {
            openStore()
        }
    }

    private fun setupRecyclerViews() {
        val rvFeatured = findViewById<RecyclerView>(R.id.rvFeatured)
        rvFeatured.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvFeatured.adapter = ProductAdapter(getFeaturedProducts())
        rvFeatured.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
        rvFeatured.scheduleLayoutAnimation()

        val rvNewArrivals = findViewById<RecyclerView>(R.id.rvNewArrivals)
        rvNewArrivals.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvNewArrivals.adapter = ProductAdapter(getNewArrivals())
        rvNewArrivals.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
        rvNewArrivals.scheduleLayoutAnimation()
    }

    private fun openStore(category: String? = null) {
        val intent = Intent(this, StoreActivity::class.java)
        category?.let { intent.putExtra("category", it) }
        startActivity(intent)
    }

    private fun getFeaturedProducts(): List<Product> {
        return listOf(
            Product(1, "Prada Leather Bag", 299.99, 399.99, R.drawable.product_women_handbag, "A luxurious leather bag from Prada, featuring premium craftsmanship and timeless design. Perfect for both casual and formal occasions."),
            Product(2, "Smart Gadget Set", 49.99, 79.99, R.drawable.product_smartwatch, "A versatile set of smart gadgets designed to make your life easier. Includes essential tech accessories for everyday use."),
            Product(3, "Designer Handbag", 89.99, 129.99, R.drawable.product_women_handbag, "Elegant designer handbag with spacious compartments and sophisticated style. Made from high-quality materials for durability."),
            Product(4, "Luxury Perfume", 79.99, 99.99, R.drawable.product_dior_sauvage, "An exquisite luxury perfume with a captivating fragrance that lasts all day. A perfect addition to your collection."),
            Product(5, "Sports Jacket", 59.99, 89.99, R.drawable.product_men_jacket, "A stylish and comfortable sports jacket designed for active lifestyles. Features breathable fabric and modern fit.")
        )
    }

    private fun getNewArrivals(): List<Product> {
        return listOf(
            Product(6, "Prada Leather Bag", 299.99, 399.99, R.drawable.product_women_handbag, "A luxurious leather bag from Prada, featuring premium craftsmanship and timeless design. Perfect for both casual and formal occasions."),
            Product(7, "Designer Handbag", 89.99, 129.99, R.drawable.product_women_handbag, "Elegant designer handbag with spacious compartments and sophisticated style. Made from high-quality materials for durability."),
            Product(8, "Luxury Perfume", 79.99, 99.99, R.drawable.product_chanel_n5, "An exquisite luxury perfume with a captivating fragrance that lasts all day. A perfect addition to your collection."),
            Product(9, "Sports Jacket", 59.99, 89.99, R.drawable.product_kids_hoodie, "A stylish and comfortable sports jacket designed for active lifestyles. Features breathable fabric and modern fit."),
            Product(10, "Smart Gadget Set", 49.99, 79.99, R.drawable.product_headphones, "A versatile set of smart gadgets designed to make your life easier. Includes essential tech accessories for everyday use.")
        )
    }

    private fun updateCartBadge() {
        val badge = bottomNavigation.getOrCreateBadge(R.id.nav_cart)
        val count = CartManager.getInstance(this).getCartCount()
        if (count > 0) {
            badge.isVisible = true
            badge.number = count
        } else {
            badge.isVisible = false
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
