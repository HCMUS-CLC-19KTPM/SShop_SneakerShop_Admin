package com.example.sshop_sneakershop_admin.Order.views

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Order.models.Order
import com.example.sshop_sneakershop_admin.databinding.OrderListItemBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OrderCardViewHolder(
    private val cardCellBinding: OrderListItemBinding,
    private val clickListener: OrderClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    fun bindItem(order: Order) {
        cardCellBinding.deliveryDescription.text = "Standard Express - " + order.id
        val formatter = SimpleDateFormat("dd - MM - yyyy")
        val startDate = formatter.format(order.startDate)
        cardCellBinding.startDate.text = startDate
        val endDate = formatter.format(order.endDate)
        cardCellBinding.endDate.text = endDate
        cardCellBinding.customerName.text = order.name
        cardCellBinding.customerPhone.text = order.phone
        cardCellBinding.customerAddress.text = order.address
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        cardCellBinding.totalCost.text = "$${df.format(order.totalCost)}"
        cardCellBinding.status.text = order.deliveryStatus
        cardCellBinding.card.setOnClickListener{
            clickListener.onClick(order)
        }
    }
}