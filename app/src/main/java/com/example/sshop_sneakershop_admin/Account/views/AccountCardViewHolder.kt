package com.example.sshop_sneakershop_admin.Account.views

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.AccountListItemBinding
import com.squareup.picasso.Picasso

class AccountCardViewHolder(
    private val cardCellBinding: AccountListItemBinding,
    private val clickListner: AccountClickListener
): RecyclerView.ViewHolder(cardCellBinding.root) {

    @SuppressLint("SetTextI18n")
    fun bindItem(account: Account) {
        if(TextUtils.isEmpty(account.avatar)) {
            cardCellBinding.accountAvatar.setImageResource(R.drawable.account_avatar)
        } else {
            Picasso.get().load(account.avatar).into(cardCellBinding.accountAvatar)
        }
        cardCellBinding.accountTextviewUsername.text = account.fullName
        if(account.email.length >= 20) {
            cardCellBinding.accountTextviewEmail.text = account.email.substring(0, 22) + "..."
        } else {
            cardCellBinding.accountTextviewEmail.text = account.email
        }
//        cardCellBinding.accountTextviewEmail.text = account.email
        if(account.id.length >= 20) {
            cardCellBinding.accountTextviewId.text = account.id.substring(0, 20) + "..."
        } else {
            cardCellBinding.accountTextviewId.text = account.id
        }
        if (account.status)
            cardCellBinding.accountTextviewStatus.text = "Active"
        else
            cardCellBinding.accountTextviewStatus.text = "Banned"
        cardCellBinding.card.setOnClickListener{
            clickListner.onClick(account)
        }
    }
}