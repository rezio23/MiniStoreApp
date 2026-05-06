package com.example.ecommerce_store

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnCart = findViewById<ImageButton>(R.id.btnCart)
        val btnOrders = findViewById<ImageButton>(R.id.btnOrders)
        val btnWishlist = findViewById<ImageButton>(R.id.btnWishlist)

        btnBack.setOnClickListener {
            finish()
        }

        btnCart.setOnClickListener {
            Toast.makeText(this, "Cart clicked", Toast.LENGTH_SHORT).show()
        }

        btnOrders.setOnClickListener {
            Toast.makeText(this, "Orders clicked", Toast.LENGTH_SHORT).show()
        }

        btnWishlist.setOnClickListener {
            Toast.makeText(this, "Wishlist clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
