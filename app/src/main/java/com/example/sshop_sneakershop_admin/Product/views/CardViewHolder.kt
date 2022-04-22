package com.example.sshop_sneakershop_admin.Product.views

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ProductListItemBinding
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat

class CardViewHolder(
    private val cardCellBinding: ProductListItemBinding,
    private val clickListener: ItemClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    @SuppressLint("SetTextI18n")
    fun bindItem(product: Product, isOnOrderDetail: Boolean) {
        if (TextUtils.isEmpty(product.image)) {
            cardCellBinding.productImage.setImageResource(R.drawable.shoe)
        } else {
            Picasso.get().load(product.image).into(cardCellBinding.productImage)
        }
        cardCellBinding.productTextviewName.text = if (product.name!!.length >= 20) "${
            product.name!!.substring(
                0,
                19
            )
        }..." else product.name
        if(isOnOrderDetail){
            cardCellBinding.productTextviewPrice.text = "$${product.price}"
            cardCellBinding.productTextviewQuantity.text = "${product.quantity}"
        }else{
            val price =
                product.price - (product.price * product.discount / 100)
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            cardCellBinding.productTextviewPrice.text = "$${df.format(price)}"
            val quantity = product.stock.sum().toString()
            cardCellBinding.productTextviewQuantity.text = quantity
        }
        cardCellBinding.productTextviewDescription.text = if (product.description!!.length >= 20) "${
            product.description!!.substring(
                0,
                80
            )
        }..." else product.description
        cardCellBinding.card.setOnClickListener {
            clickListener.onClick(product)
        }
    }
}