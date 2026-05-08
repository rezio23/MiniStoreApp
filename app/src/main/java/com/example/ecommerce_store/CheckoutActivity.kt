package com.example.ecommerce_store

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CheckoutActivity : BaseActivity() {

    private lateinit var rvItems: RecyclerView
    private lateinit var etFullName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var rbCash: RadioButton
    private lateinit var rbCard: RadioButton
    private lateinit var cardCreditCardForm: com.google.android.material.card.MaterialCardView
    private lateinit var etCardNumber: com.google.android.material.textfield.TextInputEditText
    private lateinit var etCardHolder: com.google.android.material.textfield.TextInputEditText
    private lateinit var etExpiry: com.google.android.material.textfield.TextInputEditText
    private lateinit var etCvv: com.google.android.material.textfield.TextInputEditText
    private lateinit var tvSubtotal: TextView
    private lateinit var tvShipping: TextView
    private lateinit var tvTax: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnPlaceOrder: MaterialButton

    private val shippingCost = 5.99
    private val taxRate = 0.08

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        rvItems = findViewById(R.id.rvCheckoutItems)
        etFullName = findViewById(R.id.etFullName)
        etPhone = findViewById(R.id.etPhone)
        etAddress = findViewById(R.id.etAddress)
        rbCash = findViewById(R.id.rbCash)
        rbCard = findViewById(R.id.rbCard)
        cardCreditCardForm = findViewById(R.id.cardCreditCardForm)
        etCardNumber = findViewById(R.id.etCardNumber)
        etCardHolder = findViewById(R.id.etCardHolder)
        etExpiry = findViewById(R.id.etExpiry)
        etCvv = findViewById(R.id.etCvv)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvShipping = findViewById(R.id.tvShipping)
        tvTax = findViewById(R.id.tvTax)
        tvTotal = findViewById(R.id.tvTotal)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)

        btnBack.setOnClickListener { finish() }
        btnPlaceOrder.setOnClickListener { placeOrder() }

        val paymentGroup = findViewById<RadioGroup>(R.id.paymentGroup)
        paymentGroup?.setOnCheckedChangeListener { _, checkedId ->
            cardCreditCardForm.visibility = if (checkedId == R.id.rbCard) android.view.View.VISIBLE else android.view.View.GONE
        }

        setupRecyclerView()
        calculateTotals()
        loadProfileInfo()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupBottomNavigation(bottomNav, R.id.nav_cart)
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        updateCartBadge(bottomNav)
    }

    private fun loadProfileInfo() {
        val prefs = getSharedPreferences("profile", MODE_PRIVATE)
        etFullName.setText(prefs.getString("full_name", ""))
        etPhone.setText(prefs.getString("phone", ""))
        etAddress.setText(prefs.getString("address", ""))
    }

    private fun setupRecyclerView() {
        val items = CartManager.getInstance(this).getCartItems()
        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = CheckoutAdapter(items)
    }

    private fun calculateTotals() {
        val subtotal = CartManager.getInstance(this).getCartTotal()
        val tax = subtotal * taxRate
        val total = subtotal + shippingCost + tax

        tvSubtotal.text = "$${String.format("%.2f", subtotal)}"
        tvShipping.text = "$${String.format("%.2f", shippingCost)}"
        tvTax.text = "$${String.format("%.2f", tax)}"
        tvTotal.text = "$${String.format("%.2f", total)}"
    }

    private fun placeOrder() {
        val name = etFullName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val address = etAddress.text.toString().trim()

        if (name.isEmpty()) {
            etFullName.error = "Please enter your full name"
            return
        }
        if (phone.isEmpty()) {
            etPhone.error = "Please enter your phone number"
            return
        }
        if (address.isEmpty()) {
            etAddress.error = "Please enter your address"
            return
        }

        val paymentMethod = if (rbCard.isChecked) "Credit Card" else "Cash on Delivery"

        if (rbCard.isChecked) {
            val cardNumber = etCardNumber.text.toString().trim()
            val cardHolder = etCardHolder.text.toString().trim()
            val expiry = etExpiry.text.toString().trim()
            val cvv = etCvv.text.toString().trim()

            if (cardNumber.length < 13) {
                etCardNumber.error = "Enter a valid card number"
                return
            }
            if (cardHolder.isEmpty()) {
                etCardHolder.error = "Enter cardholder name"
                return
            }
            if (!expiry.matches(Regex("""^(0[1-9]|1[0-2])/\d{2}$"""))) {
                etExpiry.error = "Format: MM/YY"
                return
            }
            if (cvv.length < 3) {
                etCvv.error = "Enter a valid CVV"
                return
            }
        }

        val order = CartManager.getInstance(this).placeOrder(
            shippingName = name,
            shippingPhone = phone,
            shippingAddress = address,
            paymentMethod = paymentMethod
        )

        if (order != null) {
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
        }
    }

    class CheckoutAdapter(private val items: List<CartItem>) :
        RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

        inner class CheckoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
            val tvName: TextView = itemView.findViewById(R.id.tvProductName)
            val tvPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
            val tvQty: TextView = itemView.findViewById(R.id.tvQuantity)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_checkout_product, parent, false)
            return CheckoutViewHolder(view)
        }

        override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
            val item = items[position]
            holder.tvName.text = item.name
            holder.tvPrice.text = "$${String.format("%.2f", item.total)}"
            holder.tvQty.text = "Qty: ${item.quantity}"
            holder.imgProduct.setImageResource(item.imageRes)
        }

        override fun getItemCount(): Int = items.size
    }
}
