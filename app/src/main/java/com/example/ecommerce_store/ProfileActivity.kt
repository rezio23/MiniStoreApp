package com.example.ecommerce_store

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : BaseActivity() {

    private lateinit var tvFullName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvAddress: TextView

    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            loadProfile()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnCart = findViewById<LinearLayout>(R.id.btnCart)
        val btnOrders = findViewById<LinearLayout>(R.id.btnOrders)
        val btnWishlist = findViewById<LinearLayout>(R.id.btnWishlist)
        val tvEditProfile = findViewById<TextView>(R.id.tvEditProfile)

        tvFullName = findViewById(R.id.tvFullName)
        tvEmail = findViewById(R.id.tvEmail)
        tvPhone = findViewById(R.id.tvPhone)
        tvAddress = findViewById(R.id.tvAddress)

        loadProfile()

        btnBack.setOnClickListener { finish() }

        btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        btnOrders.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
        }

        btnWishlist.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }

        tvEditProfile.setOnClickListener {
            editProfileLauncher.launch(Intent(this, EditProfileActivity::class.java))
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupBottomNavigation(bottomNav, R.id.nav_profile)
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        updateCartBadge(bottomNav)
    }

    private fun loadProfile() {
        val prefs = getSharedPreferences("profile", MODE_PRIVATE)
        tvFullName.text = prefs.getString("full_name", getString(R.string.profile_fullname))
        tvEmail.text = prefs.getString("email", getString(R.string.profile_email))
        tvPhone.text = prefs.getString("phone", getString(R.string.phone_placeholder))
        tvAddress.text = prefs.getString("address", getString(R.string.address_placeholder))
    }
}
