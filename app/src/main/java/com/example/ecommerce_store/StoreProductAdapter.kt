package com.example.ecommerce_store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoreProductAdapter(private var products: List<StoreProduct>) :
    RecyclerView.Adapter<StoreProductAdapter.StoreProductViewHolder>() {

    inner class StoreProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val tvOldPrice: TextView = itemView.findViewById(R.id.tvOldPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_store_product, parent, false)
        return StoreProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvProductName.text = product.name
        holder.tvCategory.text = product.category
        holder.tvProductPrice.text = "\$${String.format("%.2f", product.price)}"

        if (product.oldPrice != null) {
            holder.tvOldPrice.visibility = View.VISIBLE
            holder.tvOldPrice.text = "\$${String.format("%.2f", product.oldPrice)}"
        } else {
            holder.tvOldPrice.visibility = View.GONE
        }

        holder.imgProduct.setImageResource(product.imageRes)
    }

    override fun getItemCount(): Int = products.size

    fun updateList(newList: List<StoreProduct>) {
        products = newList
        notifyDataSetChanged()
    }
}
