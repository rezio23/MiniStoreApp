package com.example.ecommerce_store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(
    private var items: List<WishlistItem>,
    private val onRemove: (Int) -> Unit,
    private val onAddToCart: (WishlistItem) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val btnAddToCart: ImageButton = itemView.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_card, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = items[position]
        holder.tvProductName.text = item.name
        holder.tvProductPrice.text = "$${String.format("%.2f", item.price)}"
        holder.imgProduct.setImageResource(item.imageRes)

        holder.btnAddToCart.setOnClickListener {
            onAddToCart(item)
            android.widget.Toast.makeText(
                holder.itemView.context,
                "${item.name} added to cart",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        holder.itemView.setOnLongClickListener {
            onRemove(item.productId)
            true
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<WishlistItem>) {
        items = newList
        notifyDataSetChanged()
    }
}