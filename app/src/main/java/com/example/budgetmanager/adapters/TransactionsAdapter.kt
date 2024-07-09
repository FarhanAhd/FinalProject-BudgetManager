package com.example.budgetmanager.adapters

import com.example.budgetmanager.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetmanager.views.activities.MainActivity
import com.example.budgetmanager.databinding.RowTransactionBinding
import com.example.budgetmanager.models.Category
import com.example.budgetmanager.models.Transaction
import com.example.budgetmanager.utils.Constants
import com.example.budgetmanager.utils.Helper
import io.realm.RealmResults
import java.lang.String
import kotlin.Int

open class TransactionsAdapter(var context: Context, transactions: RealmResults<Transaction>?) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {
    var transactions: RealmResults<Transaction>? = transactions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction: Transaction? = transactions?.get(position)

        holder.binding.transactionAmount.setText(String.valueOf(transaction?.getAmount()))
        holder.binding.accountLbl.setText(transaction?.getAccount())

        holder.binding.transactionDate.setText(Helper.formatDate(transaction?.getDate()))
        holder.binding.transactionCategory.setText(transaction?.getCategory())

        val transactionCategory: Category? = Constants.getCategoryDetails(transaction?.getCategory())

        if (transactionCategory != null) {
            holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage())
        }
        if (transactionCategory != null) {
            holder.binding.categoryIcon.setBackgroundTintList(
                context.getColorStateList(
                    transactionCategory.getCategoryColor()
                )
            )
        }

        holder.binding.accountLbl.setBackgroundTintList(
            context.getColorStateList(
                Constants.getAccountsColor(
                    transaction?.getAccount()
                )
            )
        )

        if (transaction?.getType().equals(Constants.INCOME)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.greenColor))
        } else if (transaction?.getType().equals(Constants.EXPENSE)) {
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.redColor))
        }

        holder.itemView.setOnLongClickListener {
            val deleteDialog = AlertDialog.Builder(context).create()
            deleteDialog.setTitle("Delete Transaction")
            deleteDialog.setMessage("Are you sure to delete this transaction?")
            deleteDialog.setButton(
                DialogInterface.BUTTON_POSITIVE, "Yes"
            ) { dialogInterface: DialogInterface?, i: Int ->
                if (transaction != null) {
                    (context as MainActivity).viewModel!!.deleteTransaction(
                        transaction
                    )
                }
            }
            deleteDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE, "No"
            ) { dialogInterface: DialogInterface?, i: Int ->
                deleteDialog.dismiss()
            }
            deleteDialog.show()
            false
        }
    }

    override fun getItemCount(): Int {
        return transactions!!.size
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: RowTransactionBinding = RowTransactionBinding.bind(itemView)
    }
}