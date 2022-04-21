package com.example.sshop_sneakershop_admin.Order.views

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Order.models.Order
import com.example.sshop_sneakershop_admin.databinding.OrderListItemBinding


class OrderAdapter(
    private val orders: ArrayList<Order>,
    private val clickCListener: OrderClickListener,
    val fullOrderList: ArrayList<Order>
) : RecyclerView.Adapter<OrderCardViewHolder>(), Filterable{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = OrderListItemBinding.inflate(from, parent, false)
        return OrderCardViewHolder(binding, clickCListener)
    }

    override fun onBindViewHolder(holder: OrderCardViewHolder, position: Int) {
        holder.bindItem(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun getFilter(): Filter {
        return orderFilter
    }
    private val orderFilter: Filter = object: Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<Order> = ArrayList()
            if (constraint == null || constraint.isEmpty()){
                filteredList.addAll(fullOrderList)
            }else{
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (item in fullOrderList){
                    if (item.name.toLowerCase().contains(filterPattern)){
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            orders.clear()
            orders.addAll(results?.values as ArrayList<Order>)
            notifyDataSetChanged()
        }
    }

}

