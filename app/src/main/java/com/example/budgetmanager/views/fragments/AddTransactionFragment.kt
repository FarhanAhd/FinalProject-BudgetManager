package com.example.budgetmanager.views.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetmanager.R
import com.example.budgetmanager.adapters.AccountsAdapter
import com.example.budgetmanager.adapters.AccountsAdapter.AccountsClickListener
import com.example.budgetmanager.adapters.CategoryAdapter
import com.example.budgetmanager.adapters.CategoryAdapter.CategoryClickListener
import com.example.budgetmanager.databinding.FragmentAddTransactionBinding
import com.example.budgetmanager.databinding.ListDialogBinding
import com.example.budgetmanager.models.Account
import com.example.budgetmanager.models.Category
import com.example.budgetmanager.models.Transaction
import com.example.budgetmanager.utils.Constants
import com.example.budgetmanager.utils.Helper
import com.example.budgetmanager.views.activities.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTransactionFragment : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var binding: FragmentAddTransactionBinding? = null
    var transaction: Transaction? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTransactionBinding.inflate(inflater)


        transaction = Transaction()

        binding!!.incomeBtn.setOnClickListener { view: View? ->
            binding!!.incomeBtn.background = context!!.getDrawable(R.drawable.income_selector)
            binding!!.expenseBtn.background = context!!.getDrawable(R.drawable.default_selector)
            binding!!.expenseBtn.setTextColor(context!!.getColor(R.color.textColor))
            binding!!.incomeBtn.setTextColor(context!!.getColor(R.color.greenColor))
            transaction!!.type = Constants.INCOME
        }

        binding!!.expenseBtn.setOnClickListener { view: View? ->
            binding!!.incomeBtn.background = context!!.getDrawable(R.drawable.default_selector)
            binding!!.expenseBtn.background = context!!.getDrawable(R.drawable.expense_selector)
            binding!!.incomeBtn.setTextColor(context!!.getColor(R.color.textColor))
            binding!!.expenseBtn.setTextColor(context!!.getColor(R.color.redColor))
            transaction!!.type = Constants.EXPENSE
        }

        binding!!.date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context!!)
            datePickerDialog.setOnDateSetListener { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
                val calendar = Calendar.getInstance()
                calendar[Calendar.DAY_OF_MONTH] = datePicker.dayOfMonth
                calendar[Calendar.MONTH] = datePicker.month
                calendar[Calendar.YEAR] = datePicker.year

                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                val dateToShow = Helper.formatDate(calendar.time)

                binding!!.date.setText(dateToShow)

                transaction!!.date = calendar.time
                transaction!!.id = calendar.time.time
            }
            datePickerDialog.show()
        }

        binding!!.category.setOnClickListener { c: View? ->
            val dialogBinding = ListDialogBinding.inflate(inflater)
            val categoryDialog = AlertDialog.Builder(context).create()
            categoryDialog.setView(dialogBinding.root)


            val categoryAdapter =
                CategoryAdapter(context!!, Constants.categories, object : CategoryClickListener {
                    override fun onCategoryClicked(category: Category?) {
                        binding!!.category.setText(category!!.categoryName)
                        transaction!!.category = category.categoryName
                        categoryDialog.dismiss()
                    }
                })
            dialogBinding.recyclerView.layoutManager = GridLayoutManager(context, 3)
            dialogBinding.recyclerView.adapter = categoryAdapter
            categoryDialog.show()
        }

        binding!!.account.setOnClickListener { c: View? ->
            val dialogBinding = ListDialogBinding.inflate(inflater)
            val accountsDialog = AlertDialog.Builder(context).create()
            accountsDialog.setView(dialogBinding.root)

            val accounts = ArrayList<Account>()
            accounts.add(Account(0.0, "Cash"))
            accounts.add(Account(0.0, "Bank"))
            accounts.add(Account(0.0, "PayTM"))
            accounts.add(Account(0.0, "EasyPaisa"))
            accounts.add(Account(0.0, "Other"))

            val adapter = AccountsAdapter(context!!, accounts, object : AccountsClickListener {
                override fun onAccountSelected(account: Account?) {
                    binding!!.account.setText(account!!.accountName)
                    transaction!!.account = account.accountName
                    accountsDialog.dismiss()
                }
            })
            dialogBinding.recyclerView.layoutManager = LinearLayoutManager(context)
            //dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.adapter = adapter
            accountsDialog.show()
        }

        binding!!.saveTransactionBtn.setOnClickListener { c: View? ->
            val amount = binding!!.amount.text.toString().toDouble()
            val note = binding!!.note.text.toString()

            if (transaction!!.type == Constants.EXPENSE) {
                transaction!!.amount = amount * -1
            } else {
                transaction!!.amount = amount
            }

            transaction!!.note = note

            (activity as MainActivity?)!!.viewModel!!.addTransaction(transaction!!)
            (activity as MainActivity?)!!.transactions
            dismiss()
        }

        return binding!!.root
    }
}