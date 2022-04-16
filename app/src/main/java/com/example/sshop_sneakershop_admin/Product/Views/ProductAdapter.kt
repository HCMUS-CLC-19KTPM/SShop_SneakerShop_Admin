package com.example.sshop_sneakershop_admin.Product.Views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R


class ProductAdapter(private val products:List<Product>):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var onItemClick: ((Product) -> Unit)? = null

    inner class ViewHolder(listItemView: View): RecyclerView.ViewHolder(listItemView){
        val nameTextView = listItemView.findViewById(R.id.product_textview_name) as TextView
        val priceTextView = listItemView.findViewById(R.id.product_textview_price) as TextView
        val quantityTextView = listItemView.findViewById(R.id.product_textview_quantity) as TextView
        val descriptonTextView = listItemView.findViewById(R.id.product_textview_description) as TextView
        val imageView = listItemView.findViewById(R.id.product_image) as ImageView

        init {
            listItemView.setOnClickListener{
                onItemClick?.invoke(products[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val productView = inflater.inflate(R.layout.product_list_item, parent, false)
        return ViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nameTextView.text = product.name
        holder.priceTextView.text = product.price.toString()
        holder.quantityTextView.text = product.quantity.toString()
        holder.descriptonTextView.text = product.description
        holder.imageView.setImageResource(R.drawable.shoe)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}