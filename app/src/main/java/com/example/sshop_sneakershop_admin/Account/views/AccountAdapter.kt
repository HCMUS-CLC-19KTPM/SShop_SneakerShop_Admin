package com.example.sshop_sneakershop_admin.Account.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sshop_sneakershop_admin.Account.models.Account
import com.example.sshop_sneakershop_admin.R
import com.example.sshop_sneakershop_admin.databinding.AccountListItemBinding

class AccountAdapter(
    private val accounts: ArrayList<Account>,
    private val clickListener: AccountClickListener,
    private val fullAccountList: ArrayList<Account>
): RecyclerView.Adapter<AccountCardViewHolder>(), Filterable{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountCardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = AccountListItemBinding.inflate(from, parent, false)
        return AccountCardViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    override fun onBindViewHolder(holder: AccountCardViewHolder, position: Int) {
        holder.bindItem(accounts[position])
    }

    override fun getFilter(): Filter {
        return accountFilter
    }
    private val accountFilter: Filter = object :Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<Account> = ArrayList()
            if(constraint== null || constraint.isEmpty()) {
                filteredList.addAll(fullAccountList)
            }else{
                val filterPattern = constraint.toString().toLowerCase().trim()
                for(item in fullAccountList){
                    if(item.fullName?.toLowerCase()?.contains(filterPattern) == true){
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
            accounts.clear()
            accounts.addAll(result?.values as ArrayList<Account>)
            notifyDataSetChanged()
        }
    }
}