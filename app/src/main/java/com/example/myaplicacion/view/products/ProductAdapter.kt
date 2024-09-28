// ProductAdapter.kt
package com.example.myaplicacion.view.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myaplicacion.R
import com.example.myaplicacion.api.Product
import com.example.myaplicacion.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProductBinding.bind(itemView)

        fun bind(product: Product) {
            binding.productTitle.text = product.title
            binding.productPrice.text = "$${product.price}"
            Picasso.get().load(product.image).into(binding.productImage)
        }
    }
}
