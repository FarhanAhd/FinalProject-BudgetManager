package com.example.budgetmanager.views.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetmanager.R
import com.example.budgetmanager.adapters.TransactionsAdapter
import com.example.budgetmanager.databinding.ActivityMainBinding
import com.example.budgetmanager.models.Transaction
import com.example.budgetmanager.utils.Constants
import com.example.budgetmanager.views.fragments.AddTransactionFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var calendar: Calendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setSupportActionBar(binding!!.toolbar)
        supportActionBar!!.title = "Transactions"


        Constants.setCategories()

        calendar = Calendar.getInstance()
        updateDate()

        binding!!.nextDateBtn.setOnClickListener { c ->
            calendar.add(Calendar.DATE, 1)
            updateDate()
        }

        binding!!.previousDateBtn.setOnClickListener { c ->
            calendar.add(Calendar.DATE, -1)
            updateDate()
        }

        binding!!.floatingActionButton.setOnClickListener { c ->
            AddTransactionFragment().show(supportFragmentManager, null)
        }

        val transactions = ArrayList<Transaction>()
        transactions.add(
            Transaction(
                Constants.INCOME,
                "Business",
                "Cash",
                "Some Note Here",
                Date(),
                500.0,
                2
            )
        )
        transactions.add(
            Transaction(
                Constants.EXPENSE,
                "Investment",
                "Bank",
                "Some Note Here",
                Date(),
                900.0,
                2
            )
        )
        transactions.add(
            Transaction(
                Constants.INCOME,
                "Rent",
                "Cash",
                "Some Note Here",
                Date(),
                500.0,
                5
            )
        )
        transactions.add(
            Transaction(
                Constants.INCOME,
                "Business",
                "Cash",
                "Some Note Here",
                Date(),
                500.0,
                6
            )
        )

        val transactionsAdapter = TransactionsAdapter(this, transactions)
        binding!!.transactionsList.layoutManager = LinearLayoutManager(this)
        binding!!.transactionsList.adapter = transactionsAdapter
    }

    fun updateDate() {
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy")
        binding!!.currentDate.text = dateFormat.format(calendar!!.time)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}