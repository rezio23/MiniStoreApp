package com.example.ecommerce_store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val items: List<CartItem>,
    private val onQuantityChanged: (Int, Int) -> Unit,
    private val onRemove: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        val btnPlus: ImageButton = itemView.findViewById(R.id.btnPlus)
        val btnMinus: ImageButton = itemView.findViewById(R.id.btnMinus)
        val btnRemove: ImageButton = itemView.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.tvProductName.text = item.name
        holder.tvProductPrice.text = "$${String.format("%.2f", item.price)}"
        holder.tvQuantity.text = item.quantity.toString()
        holder.imgProduct.setImageResource(item.imageRes)

        holder.btnPlus.setOnClickListener {
            onQuantityChanged(item.productId, item.quantity + 1)
        }
        holder.btnMinus.setOnClickListener {
            onQuantityChanged(item.productId, item.quantity - 1)
        }
        holder.btnRemove.setOnClickListener {
            onRemove(item.productId)
        }
    }

    override fun getItemCount(): Int = items.size
}