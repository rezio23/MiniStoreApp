package com.example.ecommerce_store

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditProfileActivity : BaseActivity() {

    private lateinit var etFullName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etAddress: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnSave = findViewById<MaterialButton>(R.id.btnSave)
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etAddress = findViewById(R.id.etAddress)

        loadProfile()

        btnBack.setOnClickListener { finish() }
        btnSave.setOnClickListener { saveProfile() }
    }

    private fun loadProfile() {
        val prefs = getSharedPreferences("profile", MODE_PRIVATE)
        etFullName.setText(prefs.getString("full_name", getString(R.string.profile_fullname)))
        etEmail.setText(prefs.getString("email", getString(R.string.profile_email)))
        etPhone.setText(prefs.getString("phone", getString(R.string.phone_placeholder)))
        etAddress.setText(prefs.getString("address", getString(R.string.address_placeholder)))
    }

    private fun saveProfile() {
        val name = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val address = etAddress.text.toString().trim()

        if (name.isEmpty()) {
            etFullName.error = "Name is required"
            return
        }
        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            return
        }
        if (phone.isEmpty()) {
            etPhone.error = "Phone is required"
            return
        }
        if (address.isEmpty()) {
            etAddress.error = "Address is required"
            return
        }

        getSharedPreferences("profile", MODE_PRIVATE).edit().apply {
            putString("full_name", name)
            putString("email", email)
            putString("phone", phone)
            putString("address", address)
            apply()
        }

        Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }
}
