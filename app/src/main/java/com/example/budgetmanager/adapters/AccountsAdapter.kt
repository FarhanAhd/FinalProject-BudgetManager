package com.example.budgetmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetmanager.R
import com.example.budgetmanager.adapters.AccountsAdapter.AccountsViewHolder
import com.example.budgetmanager.databinding.RowAccountBinding
import com.example.budgetmanager.models.Account

class AccountsAdapter(
    var context: Context,
    var accountArrayList: ArrayList<Account>,
    var accountsClickListener: AccountsClickListener
) : RecyclerView.Adapter<AccountsViewHolder>() {
    interface AccountsClickListener {
        fun onAccountSelected(account: Account?)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        return AccountsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_account, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val account = accountArrayList[position]
        holder.binding.accountName.text = account.accountName
        holder.itemView.setOnClickListener { c: View? ->
            accountsClickListener.onAccountSelected(account)
        }
    }

    override fun getItemCount(): Int {
        return accountArrayList.size
    }

    inner class AccountsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowAccountBinding = RowAccountBinding.bind(itemView)
    }
}
