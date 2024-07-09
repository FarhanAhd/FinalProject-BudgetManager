package com.example.budgetmanager.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetmanager.R
import com.example.budgetmanager.adapters.TransactionsAdapter
import com.example.budgetmanager.databinding.FragmentTransactionsBinding
import com.example.budgetmanager.models.Transaction
import com.example.budgetmanager.utils.Constants
import com.example.budgetmanager.utils.Helper
import com.example.budgetmanager.viewmodel.MainViewModel
import com.example.budgetmanager.views.activities.MainActivity
import com.google.android.material.tabs.TabLayout
import io.realm.RealmResults
import java.util.Calendar

class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var calendar: Calendar
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        calendar = Calendar.getInstance()
        updateDate()

        binding.nextDateBtn.setOnClickListener {
            when (Constants.SELECTED_TAB) {
                Constants.DAILY -> calendar.add(Calendar.DATE, 1)
                Constants.MONTHLY -> calendar.add(Calendar.MONTH, 1)
            }
            updateDate()
        }

        binding.previousDateBtn.setOnClickListener {
            when (Constants.SELECTED_TAB) {
                Constants.DAILY -> calendar.add(Calendar.DATE, -1)
                Constants.MONTHLY -> calendar.add(Calendar.MONTH, -1)
            }
            updateDate()
        }

        binding.floatingActionButton.setOnClickListener {
            AddTransactionFragment().show(parentFragmentManager, null)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.text) {
                    "Monthly" -> {
                        Constants.SELECTED_TAB = 1
                        updateDate()
                    }
                    "Daily" -> {
                        Constants.SELECTED_TAB = 0
                        updateDate()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.transactionsList.layoutManager = LinearLayoutManager(context)

        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            val transactionsAdapter = transactions?.let { activity?.let { it1 ->
                TransactionsAdapter(
                    it1, it)
            } }
            binding.transactionsList.adapter = transactionsAdapter
            if (transactions != null) {
                binding.emptyState.visibility = if (transactions.size > 0) View.GONE else View.VISIBLE
            }
        }

        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            binding.incomeLbl.text = income.toString()
        }

        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            binding.expenseLbl.text = expense.toString()
        }

        viewModel.totalAmount.observe(viewLifecycleOwner) { total ->
            binding.totalLbl.text = total.toString()
        }

        viewModel.getTransactions(calendar)

        return binding.root
    }

    private fun updateDate() {
        binding.currentDate.text = when (Constants.SELECTED_TAB) {
            Constants.DAILY -> Helper.formatDate(calendar.time)
            Constants.MONTHLY -> Helper.formatDateByMonth(calendar.time)
            else -> ""
        }
        viewModel.getTransactions(calendar)
    }
}