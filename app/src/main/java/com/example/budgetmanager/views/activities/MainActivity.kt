package com.example.budgetmanager.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetmanager.R
import com.example.budgetmanager.adapters.TransactionsAdapter
import com.example.budgetmanager.databinding.ActivityMainBinding
import com.example.budgetmanager.utils.Constants
import com.example.budgetmanager.utils.Helper
import com.example.budgetmanager.viewmodel.MainViewModel
import com.example.budgetmanager.views.fragments.AddTransactionFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    var calendar: Calendar? = null

    var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        setSupportActionBar(binding!!.toolBar)
        supportActionBar!!.title = "Transactions"


        Constants.setCategories()

        calendar = Calendar.getInstance()
        updateDate()

        binding!!.nextDateBtn.setOnClickListener { c ->
            if (Constants.SELECTED_TAB === Constants.DAILY) {
                calendar!!.add(Calendar.DATE, 1)
            } else if (Constants.SELECTED_TAB === Constants.MONTHLY) {
                calendar!!.add(Calendar.MONTH, 1)
            }
            updateDate()
        }

        binding!!.previousDateBtn.setOnClickListener { c ->
            if (Constants.SELECTED_TAB === Constants.DAILY) {
                calendar!!.add(Calendar.DATE, -1)
            } else if (Constants.SELECTED_TAB === Constants.MONTHLY) {
                calendar!!.add(Calendar.MONTH, -1)
            }
            updateDate()
        }


        binding!!.floatingActionButton.setOnClickListener { c ->
            AddTransactionFragment().show(supportFragmentManager, null)
        }


        binding!!.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text == "Monthly") {
                    Constants.SELECTED_TAB = 1
                    updateDate()
                } else if (tab.text == "Daily") {
                    Constants.SELECTED_TAB = 0
                    updateDate()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })


        binding!!.transactionsList.setLayoutManager(LinearLayoutManager(this))


        /*viewModel!!.transactions.observe(this, object : Observer<RealmResults<Transaction>?> {
            override fun onChanged(value: RealmResults<Transaction?>) {
                val transactionsAdapter = TransactionsAdapter(this@MainActivity, transactions)
                binding!!.transactionsList.adapter = transactionsAdapter
                if (transactions.size > 0) {
                    binding!!.emptyState.visibility = View.GONE
                } else {
                    binding!!.emptyState.visibility = View.VISIBLE
                }
            }
        })*/

        //This is probably the one causing the problem?
        viewModel!!.transactions.observe(this, Observer { transactions ->
            val transactionsAdapter = TransactionsAdapter(this, transactions)
            binding!!.transactionsList.adapter = transactionsAdapter
            if (transactions?.size!! > 0) {
                binding!!.emptyState.visibility = View.GONE
            } else {
                binding!!.emptyState.visibility = View.VISIBLE
            }
        })

        viewModel!!.totalIncome.observe(this, object : Observer<Double?> {
            override fun onChanged(aDouble: Double?) {
                binding!!.incomeLbl.setText(aDouble.toString())
            }
        })

        viewModel!!.totalExpense.observe(this, object : Observer<Double?> {
            override fun onChanged(aDouble: Double?) {
                binding!!.expenseLbl.setText(aDouble.toString())
            }
        })

        viewModel!!.totalAmount.observe(this, object : Observer<Double?> {
            override fun onChanged(aDouble: Double?) {
                binding!!.totalLbl.setText(aDouble.toString())
            }
        })
        viewModel!!.getTransactions(calendar)
    }

    val transactions: Unit
        get() {
            viewModel!!.getTransactions(calendar)
        }


    fun updateDate() {
        if (Constants.SELECTED_TAB === Constants.DAILY) {
            binding!!.currentDate.setText(Helper.formatDate(calendar!!.time))
        } else if (Constants.SELECTED_TAB === Constants.MONTHLY) {
            binding!!.currentDate.setText(Helper.formatDateByMonth(calendar!!.time))
        }
        viewModel!!.getTransactions(calendar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}