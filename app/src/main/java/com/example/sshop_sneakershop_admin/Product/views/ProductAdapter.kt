package com.example.sshop_sneakershop_admin.Product.views

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.databinding.ProductListItemBinding


class ProductAdapter(
    private val products: ArrayList<Product>,
    private val clickListener: ItemClickListener,
    private val fullProductList: ArrayList<Product>,
    private val isOnOrderDetail: Boolean
) : RecyclerView.Adapter<CardViewHolder>(), Filterable
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ProductListItemBinding.inflate(from, parent, false)
        return CardViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindItem(products[position], isOnOrderDetail)
    }

    override fun getFilter(): Filter {
        return productFilter
    }
    private val productFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: ArrayList<Product> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(fullProductList)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in fullProductList) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            Log.i("productAdapter", "${fullProductList.size}")
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            products.clear()
            products.addAll(results.values as ArrayList<Product>)
            notifyDataSetChanged()
        }
    }
}
