package com.example.ecommerce_store

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class StoreActivity : AppCompatActivity() {

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
            StoreProduct(1, "Men Casual Shirt", 29.99, 49.99, R.drawable.product_prada, "Men"),
            StoreProduct(2, "Men Denim Jacket", 59.99, 89.99, R.drawable.product_jacket, "Men"),
            StoreProduct(3, "Men Polo Tee", 24.99, 39.99, R.drawable.product_bag, "Men"),
            StoreProduct(4, "Men Chinos", 34.99, 54.99, R.drawable.product_gadget, "Men"),
            StoreProduct(5, "Women Summer Dress", 39.99, 59.99, R.drawable.product_prada, "Women"),
            StoreProduct(6, "Women Blouse", 29.99, 44.99, R.drawable.product_bag, "Women"),
            StoreProduct(7, "Women Skirt", 19.99, 34.99, R.drawable.product_gadget, "Women"),
            StoreProduct(8, "Women Handbag", 49.99, 79.99, R.drawable.product_bag, "Women"),
            StoreProduct(9, "Kids T-Shirt", 14.99, 24.99, R.drawable.product_prada, "Kids"),
            StoreProduct(10, "Kids Shorts", 12.99, 19.99, R.drawable.product_gadget, "Kids"),
            StoreProduct(11, "Kids Sneakers", 34.99, 49.99, R.drawable.product_bag, "Kids"),
            StoreProduct(12, "Kids Hoodie", 24.99, 39.99, R.drawable.product_jacket, "Kids"),
            StoreProduct(13, "Running Shoes", 59.99, 89.99, R.drawable.product_bag, "Sports"),
            StoreProduct(14, "Sports Shorts", 19.99, 29.99, R.drawable.product_gadget, "Sports"),
            StoreProduct(15, "Yoga Mat", 24.99, 39.99, R.drawable.product_prada, "Sports"),
            StoreProduct(16, "Gym Gloves", 12.99, 19.99, R.drawable.product_bag, "Sports"),
            StoreProduct(17, "Dior Sauvage", 89.99, 119.99, R.drawable.product_perfume, "Perfume"),
            StoreProduct(18, "Chanel No.5", 99.99, 149.99, R.drawable.product_perfume, "Perfume"),
            StoreProduct(19, "Armani Code", 79.99, 109.99, R.drawable.product_jacket, "Perfume"),
            StoreProduct(20, "Versace Eros", 69.99, 99.99, R.drawable.product_gadget, "Perfume"),
            StoreProduct(21, "Leather Wallet", 24.99, 34.99, R.drawable.product_bag, "Accessories"),
            StoreProduct(22, "Sunglasses", 34.99, 49.99, R.drawable.product_gadget, "Accessories"),
            StoreProduct(23, "Smart Watch", 129.99, 159.99, R.drawable.product_prada, "Accessories"),
            StoreProduct(24, "Backpack", 44.99, 59.99, R.drawable.product_jacket, "Accessories"),
            StoreProduct(25, "Wireless Headphones", 49.99, 79.99, R.drawable.product_gadget, "Accessories")
        )
    }
}
