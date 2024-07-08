package com.example.budgetmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetmanager.R
import com.example.budgetmanager.adapters.TransactionsAdapter.TransactionViewHolder
import com.example.budgetmanager.databinding.RowTransactionBinding
import com.example.budgetmanager.models.Transaction
import com.example.budgetmanager.utils.Constants
import com.example.budgetmanager.utils.Helper

class TransactionsAdapter(var context: Context, var transactions: ArrayList<Transaction>) :
    RecyclerView.Adapter<TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.binding.transactionAmount.text = transaction.amount.toString()
        holder.binding.accountLbl.text = transaction.account

        holder.binding.transactionDate.text = Helper.formatDate(transaction.date)
        holder.binding.transactionCategory.text = transaction.category

        val transactionCategory = transaction.category?.let { Constants.getCategoryDetails(it) }

        if (transactionCategory != null) {
            holder.binding.categoryIcon.setImageResource(transactionCategory.categoryImage)
        }
        if (transactionCategory != null) {
            holder.binding.categoryIcon.backgroundTintList =
                context.getColorStateList(transactionCategory.categoryColor)
        }

        holder.binding.accountLbl.backgroundTintList = context.getColorStateList(
            Constants.getAccountsColor(transaction.account)
        )

        if (transaction.type == Constants.INCOME) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor))
        } else if (transaction.type == Constants.EXPENSE) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor))
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowTransactionBinding = RowTransactionBinding.bind(itemView)
    }
}