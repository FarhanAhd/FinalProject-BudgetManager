package com.example.budgetmanager.views.fragments

import com.example.budgetmanager.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetmanager.adapters.AccountsAdapter
import com.example.budgetmanager.adapters.AccountsAdapter.AccountsClickListener
import com.example.budgetmanager.adapters.CategoryAdapter
import com.example.budgetmanager.adapters.CategoryAdapter.CategoryClickListener
import com.example.budgetmanager.views.activities.MainActivity
import com.example.budgetmanager.databinding.FragmentAddTransactionBinding
import com.example.budgetmanager.databinding.ListDialogBinding
import com.example.budgetmanager.models.Account
import com.example.budgetmanager.models.Category
import com.example.budgetmanager.models.Transaction
import com.example.budgetmanager.utils.Constants
import com.example.budgetmanager.utils.Helper
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

        binding!!.incomeBtn.setOnClickListener { view ->
            binding!!.incomeBtn.setBackground(context?.getDrawable(R.drawable.income_selector))
            binding!!.expenseBtn.setBackground(context?.getDrawable(R.drawable.default_selector))
            context?.let { binding!!.incomeBtn.setTextColor(it.getColor(R.color.greenColor)) }
            context?.let { binding!!.expenseBtn.setTextColor(it.getColor(R.color.textColor)) }
            transaction!!.setType(Constants.INCOME)
        }

        binding!!.expenseBtn.setOnClickListener { view ->
            binding!!.incomeBtn.setBackground(context?.getDrawable(R.drawable.default_selector))
            binding!!.expenseBtn.setBackground(context?.getDrawable(R.drawable.expense_selector))
            context?.let { binding!!.incomeBtn.setTextColor(it.getColor(R.color.textColor)) }
            context?.let { binding!!.expenseBtn.setTextColor(it.getColor(R.color.redColor)) }
            transaction!!.setType(Constants.EXPENSE)
        }

        binding!!.date.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext())
            datePickerDialog.setOnDateSetListener { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
                val calendar = Calendar.getInstance()
                calendar[Calendar.DAY_OF_MONTH] = datePicker.dayOfMonth
                calendar[Calendar.MONTH] = datePicker.month
                calendar[Calendar.YEAR] = datePicker.year

                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
                val dateToShow: String = Helper.formatDate(calendar.time)

                binding!!.date.setText(dateToShow)

                transaction!!.setDate(calendar.time)
                transaction!!.setId(calendar.time.time)
            }
            datePickerDialog.show()
        })

        binding!!.category.setOnClickListener { c ->
            val dialogBinding: ListDialogBinding = ListDialogBinding.inflate(inflater)
            val categoryDialog =
                AlertDialog.Builder(context).create()
            categoryDialog.setView(dialogBinding.getRoot())


            val categoryAdapter = CategoryAdapter(
                requireContext(),
                Constants.categories,
                object : CategoryClickListener {
                    override fun onCategoryClicked(category: Category?) {
                        binding!!.category.setText(category?.getCategoryName())
                        transaction!!.setCategory(category?.getCategoryName())
                        categoryDialog.dismiss()
                    }
                })
            dialogBinding.recyclerView.setLayoutManager(GridLayoutManager(context, 3))
            dialogBinding.recyclerView.setAdapter(categoryAdapter)
            categoryDialog.show()
        }

        binding!!.account.setOnClickListener { c ->
            val dialogBinding: ListDialogBinding = ListDialogBinding.inflate(inflater)
            val accountsDialog =
                AlertDialog.Builder(context).create()
            accountsDialog.setView(dialogBinding.getRoot())

            val accounts: ArrayList<Account> = ArrayList<Account>()
            accounts.add(Account(0.0, "Cash"))
            accounts.add(Account(0.0, "Bank"))
            accounts.add(Account(0.0, "PayTM"))
            accounts.add(Account(0.0, "EasyPaisa"))
            accounts.add(Account(0.0, "Other"))

            val adapter =
                AccountsAdapter(requireContext(), accounts, object : AccountsClickListener {
                    override fun onAccountSelected(account: Account?) {
                        binding!!.account.setText(account?.getAccountName())
                        transaction!!.setAccount(account?.getAccountName())
                        accountsDialog.dismiss()
                    }
                })
            dialogBinding.recyclerView.setLayoutManager(LinearLayoutManager(context))
            dialogBinding.recyclerView.setAdapter(adapter)
            accountsDialog.show()
        }

        binding!!.saveTransactionBtn.setOnClickListener { c ->
            val amount: Double = binding!!.amount.getText().toString().toDouble()
            val note: String = binding!!.note.getText().toString()

            if (transaction!!.getType().equals(Constants.EXPENSE)) {
                transaction!!.setAmount(amount * -1)
            } else {
                transaction!!.setAmount(amount)
            }

            transaction!!.setNote(note)

            (activity as MainActivity?)!!.viewModel!!.addTransaction(transaction)
            (activity as MainActivity?)!!.transactions
            dismiss()
        }

        return binding!!.getRoot()
    }
}