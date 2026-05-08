package com.example.ecommerce_store

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class StoreActivity : BaseActivity() {

    private lateinit var adapter: StoreProductAdapter
    private lateinit var rvStoreProducts: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var btnClear: ImageButton
    private lateinit var tvNoResults: TextView
    private lateinit var btnBack: ImageButton

    private val allProducts = getAllProducts()
    private var selectedCategory = "All"

    private lateinit var categoryButtons: Map<String, MaterialButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        initViews()
        setupRecyclerView()
        setupSearch()
        setupCategoryToggles()

        btnBack.setOnClickListener { finish() }

        intent.getStringExtra("category")?.let {
            if (categoryButtons.containsKey(it)) {
                selectedCategory = it
                updateCategoryUI()
                filterProducts()
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupBottomNavigation(bottomNav, R.id.nav_stores)
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        updateCartBadge(bottomNav)
    }

    private fun initViews() {
        rvStoreProducts = findViewById(R.id.rvStoreProducts)
        etSearch = findViewById(R.id.etSearch)
        btnClear = findViewById(R.id.btnClear)
        tvNoResults = findViewById(R.id.tvNoResults)
        btnBack = findViewById(R.id.btnBack)

        categoryButtons = mapOf(
            "All" to findViewById(R.id.btnCategoryAll),
            "Men" to findViewById(R.id.btnCategoryMen),
            "Women" to findViewById(R.id.btnCategoryWomen),
            "Kids" to findViewById(R.id.btnCategoryKids),
            "Sports" to findViewById(R.id.btnCategorySports),
            "Perfume" to findViewById(R.id.btnCategoryPerfume),
            "Accessories" to findViewById(R.id.btnCategoryAccessories)
        )
    }

    private fun setupRecyclerView() {
        rvStoreProducts.layoutManager = GridLayoutManager(this, 2)
        adapter = StoreProductAdapter(allProducts)
        rvStoreProducts.adapter = adapter
        rvStoreProducts.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fall_down)
        rvStoreProducts.scheduleLayoutAnimation()
    }

    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                btnClear.visibility = if (query.isEmpty()) View.GONE else View.VISIBLE
                filterProducts()
            }
        })

        btnClear.setOnClickListener {
            etSearch.text.clear()
        }
    }

    private fun setupCategoryToggles() {
        categoryButtons.forEach { (category, button) ->
            button.setOnClickListener {
                selectedCategory = category
                updateCategoryUI()
                filterProducts()
                Toast.makeText(this, category, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateCategoryUI() {
        categoryButtons.forEach { (category, button) ->
            if (category == selectedCategory) {
                button.setBackgroundColor(getColor(R.color.primary))
                button.setTextColor(getColor(R.color.white))
                button.strokeWidth = 0
            } else {
                button.setBackgroundColor(getColor(R.color.card_background))
                button.setTextColor(getColor(R.color.text_primary))
                button.strokeWidth = 1
                button.setStrokeColor(android.content.res.ColorStateList.valueOf(getColor(R.color.divider)))
            }
        }
    }

    private fun filterProducts() {
        val query = etSearch.text.toString().trim().lowercase()

        val filtered = allProducts.filter { product ->
            val matchesCategory = selectedCategory == "All" || product.category == selectedCategory
            val matchesSearch = query.isEmpty() || product.name.lowercase().contains(query)
            matchesCategory && matchesSearch
        }

        adapter.updateList(filtered)
        tvNoResults.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        rvStoreProducts.visibility = if (filtered.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun getAllProducts(): List<StoreProduct> {
        return listOf(
            StoreProduct(1, "Men Casual Shirt", 29.99, 49.99, R.drawable.product_prada, "Men", "A comfortable casual shirt for men, perfect for everyday wear. Features soft fabric and a relaxed fit."),
            StoreProduct(2, "Men Denim Jacket", 59.99, 89.99, R.drawable.product_jacket, "Men", "Classic denim jacket with a modern twist. Durable, stylish, and perfect for layering in any season."),
            StoreProduct(3, "Men Polo Tee", 24.99, 39.99, R.drawable.product_bag, "Men", "Premium polo t-shirt with a tailored fit. Breathable cotton fabric keeps you cool and comfortable all day."),
            StoreProduct(4, "Men Chinos", 34.99, 54.99, R.drawable.product_gadget, "Men", "Versatile chino pants that transition seamlessly from office to weekend. Slim fit with stretch for comfort."),
            StoreProduct(5, "Women Summer Dress", 39.99, 59.99, R.drawable.product_prada, "Women", "A breezy summer dress with a flattering silhouette. Lightweight fabric and vibrant colors for sunny days."),
            StoreProduct(6, "Women Blouse", 29.99, 44.99, R.drawable.product_bag, "Women", "Elegant blouse with delicate details. Perfect for both professional settings and casual outings."),
            StoreProduct(7, "Women Skirt", 19.99, 34.99, R.drawable.product_gadget, "Women", "A chic skirt that pairs beautifully with any top. Features a comfortable waistband and flowing design."),
            StoreProduct(8, "Women Handbag", 49.99, 79.99, R.drawable.product_bag, "Women", "Stylish handbag with multiple compartments for organized storage. Crafted from quality materials."),
            StoreProduct(9, "Kids T-Shirt", 14.99, 24.99, R.drawable.product_prada, "Kids", "Fun and colorful t-shirt for kids. Soft, durable fabric that withstands active play and frequent washing."),
            StoreProduct(10, "Kids Shorts", 12.99, 19.99, R.drawable.product_gadget, "Kids", "Comfortable shorts designed for kids on the move. Elastic waistband ensures a perfect fit every time."),
            StoreProduct(11, "Kids Sneakers", 34.99, 49.99, R.drawable.product_bag, "Kids", "Trendy sneakers with cushioned soles for all-day comfort. Durable design that keeps up with active kids."),
            StoreProduct(12, "Kids Hoodie", 24.99, 39.99, R.drawable.product_jacket, "Kids", "Cozy hoodie perfect for cooler days. Soft fleece lining and a fun design kids will love."),
            StoreProduct(13, "Running Shoes", 59.99, 89.99, R.drawable.product_bag, "Sports", "High-performance running shoes with advanced cushioning. Designed for speed, comfort, and endurance."),
            StoreProduct(14, "Sports Shorts", 19.99, 29.99, R.drawable.product_gadget, "Sports", "Lightweight sports shorts with moisture-wicking technology. Stay cool and comfortable during any workout."),
            StoreProduct(15, "Yoga Mat", 24.99, 39.99, R.drawable.product_prada, "Sports", "Premium yoga mat with excellent grip and cushioning. Non-slip surface for safe and effective practice."),
            StoreProduct(16, "Gym Gloves", 12.99, 19.99, R.drawable.product_bag, "Sports", "Durable gym gloves that protect your hands during workouts. Breathable design with reinforced padding."),
            StoreProduct(17, "Dior Sauvage", 89.99, 119.99, R.drawable.product_perfume, "Perfume", "An iconic fragrance from Dior with fresh and spicy notes. Bold, masculine, and irresistibly captivating."),
            StoreProduct(18, "Chanel No.5", 99.99, 149.99, R.drawable.product_perfume, "Perfume", "The legendary Chanel No.5, a timeless floral aldehyde fragrance. Elegant, sophisticated, and unforgettable."),
            StoreProduct(19, "Armani Code", 79.99, 109.99, R.drawable.product_jacket, "Perfume", "A seductive scent with notes of leather, tobacco, and citrus. Perfect for evening wear and special occasions."),
            StoreProduct(20, "Versace Eros", 69.99, 99.99, R.drawable.product_gadget, "Perfume", "A vibrant fragrance inspired by Greek mythology. Fresh mint, green apple, and vanilla create a powerful aroma."),
            StoreProduct(21, "Leather Wallet", 24.99, 34.99, R.drawable.product_bag, "Accessories", "Sleek leather wallet with multiple card slots and a bill compartment. Compact design that fits any pocket."),
            StoreProduct(22, "Sunglasses", 34.99, 49.99, R.drawable.product_gadget, "Accessories", "Fashionable sunglasses with UV protection. Trendy frames that complement any face shape and outfit."),
            StoreProduct(23, "Smart Watch", 129.99, 159.99, R.drawable.product_prada, "Accessories", "A feature-packed smartwatch with fitness tracking and notifications. Stay connected and monitor your health."),
            StoreProduct(24, "Backpack", 44.99, 59.99, R.drawable.product_jacket, "Accessories", "Spacious backpack with padded laptop compartment. Ideal for school, work, or travel with ergonomic design."),
            StoreProduct(25, "Wireless Headphones", 49.99, 79.99, R.drawable.product_gadget, "Accessories", "Premium wireless headphones with noise cancellation. Immerse yourself in crystal-clear audio anywhere.")
        )
    }
}
