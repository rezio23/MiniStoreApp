package com.example.ecommerce_store

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchNotifications: SwitchCompat
    private lateinit var switchDarkMode: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        switchNotifications = findViewById(R.id.switchNotifications)
        switchDarkMode = findViewById(R.id.switchDarkMode)

        btnBack.setOnClickListener { finish() }

        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        switchNotifications.isChecked = prefs.getBoolean("notifications", true)
        switchDarkMode.isChecked = prefs.getBoolean("dark_mode", false)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications", isChecked).apply()
            Toast.makeText(this, "Notifications ${if (isChecked) "enabled" else "disabled"}", Toast.LENGTH_SHORT).show()
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            Toast.makeText(this, "Dark mode ${if (isChecked) "enabled" else "disabled"}", Toast.LENGTH_SHORT).show()
        }

        findViewById<android.widget.LinearLayout>(R.id.rowAbout).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("About")
                .setMessage("E-Store v1.0\n\nYour one-stop shopping destination.")
                .setPositiveButton("OK", null)
                .show()
        }

        findViewById<android.widget.LinearLayout>(R.id.rowPrivacy).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Privacy Policy")
                .setMessage("We value your privacy. Your data is stored locally on your device and is not shared with third parties.")
                .setPositiveButton("OK", null)
                .show()
        }

        findViewById<android.widget.LinearLayout>(R.id.rowClearData).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Clear All Data")
                .setMessage("This will permanently delete your cart, wishlist, and orders. Continue?")
                .setPositiveButton("Clear") { _, _ ->
                    getSharedPreferences("ecommerce_prefs", Context.MODE_PRIVATE).edit().clear().apply()
                    Toast.makeText(this, "All data cleared", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}