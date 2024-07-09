package com.example.budgetmanager.views.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.budgetmanager.R
import com.example.budgetmanager.databinding.ActivityMainBinding
import com.example.budgetmanager.utils.Constants
import com.example.budgetmanager.viewmodel.MainViewModel
import com.example.budgetmanager.views.fragments.StatsFragment
import com.example.budgetmanager.views.fragments.TransactionsFragment
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null

    var calendar: Calendar? = null


    /*
    0 = Daily
    1 = Monthly
    2 = Calendar
    3 = Summary
    4 = Notes
     */
    @JvmField
    var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)



        setSupportActionBar(binding!!.toolBar)
        supportActionBar!!.title = "Transactions"


        Constants.setCategories()

        calendar = Calendar.getInstance()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, TransactionsFragment())
        transaction.commit()

        binding!!.bottomNavigationView.setOnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()
            if (item.itemId == R.id.transactions) {
                supportFragmentManager.popBackStack()
            } else if (item.itemId == R.id.stats) {
                transaction.replace(R.id.content, StatsFragment())
                transaction.addToBackStack(null)
            }
            transaction.commit()
            true
        }
    }

    val transactions: Unit
        get() {
            viewModel!!.getTransactions(calendar)
        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}