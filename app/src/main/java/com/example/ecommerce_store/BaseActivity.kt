package com.example.ecommerce_store

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isDark = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        super.onCreate(savedInstanceState)
    }

    protected fun setupBottomNavigation(bottomNav: BottomNavigationView, selectedItemId: Int) {
        bottomNav.selectedItemId = selectedItemId
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> navigateTo(MainActivity::class.java)
                R.id.nav_stores -> navigateTo(StoreActivity::class.java)
                R.id.nav_cart -> navigateTo(CartActivity::class.java)
                R.id.nav_profile -> navigateTo(ProfileActivity::class.java)
            }
            true
        }
        updateCartBadge(bottomNav)
    }

    protected fun updateCartBadge(bottomNav: BottomNavigationView) {
        val badge = bottomNav.getOrCreateBadge(R.id.nav_cart)
        val count = CartManager.getInstance(this).getCartCount()
        if (count > 0) {
            badge.isVisible = true
            badge.number = count
        } else {
            badge.isVisible = false
        }
    }

    private fun <T : AppCompatActivity> navigateTo(target: Class<T>) {
        if (this::class.java != target) {
            val intent = Intent(this, target).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            }
            startActivity(intent)
        }
    }
}
