package com.example.ecommerce_store

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val tvOldPrice: TextView = itemView.findViewById(R.id.tvOldPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvProductName.text = product.name
        holder.tvProductPrice.text = "$${String.format("%.2f", product.price)}"

        if (product.oldPrice != null) {
            holder.tvOldPrice.visibility = View.VISIBLE
            holder.tvOldPrice.text = "$${String.format("%.2f", product.oldPrice)}"
        } else {
            holder.tvOldPrice.visibility = View.GONE
        }

        holder.imgProduct.setImageResource(product.imageRes)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra("product_id", product.id)
                putExtra("product_name", product.name)
                putExtra("product_price", product.price)
                putExtra("product_old_price", product.oldPrice ?: -1.0)
                putExtra("product_image", product.imageRes)
                putExtra("product_category", "")
                putExtra("product_description", product.description)
            }
            context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            CartManager.getInstance(holder.itemView.context).toggleWishlist(product)
            android.widget.Toast.makeText(
                holder.itemView.context,
                "${product.name} added to wishlist",
                android.widget.Toast.LENGTH_SHORT
            ).show()
            true
        }
    }

    override fun getItemCount(): Int = products.size
}