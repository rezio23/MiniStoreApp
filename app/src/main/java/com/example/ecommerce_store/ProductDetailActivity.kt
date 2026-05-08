package com.example.ecommerce_store

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class ProductDetailActivity : BaseActivity() {

    private var quantity = 1
    private var productPrice = 0.0
    private var productId = 0
    private lateinit var productName: String
    private var productImageRes = 0
    private var productCategory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        productId = intent.getIntExtra("product_id", 0)
        productName = intent.getStringExtra("product_name") ?: ""
        productPrice = intent.getDoubleExtra("product_price", 0.0)
        val oldPrice = intent.getDoubleExtra("product_old_price", -1.0)
        productImageRes = intent.getIntExtra("product_image", R.drawable.ic_store)
        productCategory = intent.getStringExtra("product_category") ?: ""
        val productDescription = intent.getStringExtra("product_description") ?: ""

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnWishlist = findViewById<ImageButton>(R.id.btnWishlist)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)
        val tvCategory = findViewById<TextView>(R.id.tvCategory)
        val tvProductName = findViewById<TextView>(R.id.tvProductName)
        val tvProductPrice = findViewById<TextView>(R.id.tvProductPrice)
        val tvOldPrice = findViewById<TextView>(R.id.tvOldPrice)
        val tvTotalPrice = findViewById<TextView>(R.id.tvTotalPrice)
        val btnMinus = findViewById<ImageButton>(R.id.btnMinus)
        val btnPlus = findViewById<ImageButton>(R.id.btnPlus)
        val tvQuantity = findViewById<TextView>(R.id.tvQuantity)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val btnAddToCart = findViewById<MaterialButton>(R.id.btnAddToCart)

        imgProduct.setImageResource(productImageRes)
        tvProductName.text = productName
        tvProductPrice.text = "$${String.format("%.2f", productPrice)}"
        tvCategory.text = productCategory.ifEmpty { "Product" }
        tvDescription.text = productDescription.ifEmpty { getString(R.string.description_placeholder) }

        if (oldPrice > 0) {
            tvOldPrice.visibility = TextView.VISIBLE
            tvOldPrice.text = "$${String.format("%.2f", oldPrice)}"
        } else {
            tvOldPrice.visibility = TextView.GONE
        }

        btnBack.setOnClickListener { finish() }

        btnWishlist.setOnClickListener {
            CartManager.getInstance(this).toggleWishlist(
                Product(productId, productName, productPrice, if (oldPrice > 0) oldPrice else null, productImageRes, productDescription)
            )
            Toast.makeText(this, "$productName added to wishlist", Toast.LENGTH_SHORT).show()
        }

        fun updateTotal() {
            tvQuantity.text = quantity.toString()
            tvTotalPrice.text = "$${String.format("%.2f", productPrice * quantity)}"
        }

        btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateTotal()
            }
        }

        btnPlus.setOnClickListener {
            quantity++
            updateTotal()
        }

        btnAddToCart.setOnClickListener {
            repeat(quantity) {
                CartManager.getInstance(this).addToCart(
                    Product(productId, productName, productPrice, if (oldPrice > 0) oldPrice else null, productImageRes, productDescription)
                )
            }
            Toast.makeText(this, "$quantity x $productName added to cart", Toast.LENGTH_SHORT).show()
        }

        updateTotal()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupBottomNavigation(bottomNav, R.id.nav_stores)
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        updateCartBadge(bottomNav)
    }
}
