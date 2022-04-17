package com.example.sshop_sneakershop_admin.Product.Views

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Product.models.Product
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.ProductListItemBinding
import com.squareup.picasso.Picasso

class CardViewHolder(
    private val cardCellBinding: ProductListItemBinding,
    private val clickListener: ItemClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindItem(product: Product) {
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
        val price = "$" + product.price.toString()
        cardCellBinding.productTextviewPrice.text = price
        val quantity = product.stock.sum().toString()
        cardCellBinding.productTextviewQuantity.text = quantity
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