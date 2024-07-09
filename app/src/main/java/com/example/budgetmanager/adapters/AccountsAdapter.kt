package com.example.budgetmanager.adapters

import com.example.budgetmanager.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetmanager.databinding.RowAccountBinding
import com.example.budgetmanager.models.Account

class AccountsAdapter(
    var context: Context, accountArrayList: ArrayList<Account>,
    var accountsClickListener: AccountsClickListener
) :
    RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder>() {
    var accountArrayList: ArrayList<Account> = accountArrayList

    interface AccountsClickListener {
        fun onAccountSelected(account: Account?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        return AccountsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_account, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        val account: Account = accountArrayList[position]
        holder.binding.accountName.setText(account.getAccountName())
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