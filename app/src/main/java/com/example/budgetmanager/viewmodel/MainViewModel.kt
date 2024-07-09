package com.example.budgetmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.budgetmanager.models.Transaction
import com.example.budgetmanager.utils.Constants
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.deleteFromRealm
import java.util.Calendar
import java.util.Date

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var transactions: MutableLiveData<RealmResults<Transaction>?> =
        MutableLiveData<RealmResults<Transaction>?>()

    var totalIncome: MutableLiveData<Double> = MutableLiveData()
    var totalExpense: MutableLiveData<Double> = MutableLiveData()
    var totalAmount: MutableLiveData<Double> = MutableLiveData()

    var realm: Realm? = null
    var calendar: Calendar? = null

    init {
        Realm.init(application)
        setupDatabase()
    }

    fun getTransactions(calendar: Calendar?) {
        this.calendar = calendar
        calendar!![Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        var income = 0.0
        var expense = 0.0
        var total = 0.0
        var newTransactions: RealmResults<Transaction>? = null
        if (Constants.SELECTED_TAB === Constants.DAILY) {
            // Select * from transactions
            // Select * from transactions where id = 5

            newTransactions = realm!!.where(Transaction::class.java)
                .greaterThanOrEqualTo("date", calendar.time)
                .lessThan("date", Date(calendar.time.time + (24 * 60 * 60 * 1000)))
                .findAll()

            income = realm!!.where(Transaction::class.java)
                .greaterThanOrEqualTo("date", calendar.time)
                .lessThan("date", Date(calendar.time.time + (24 * 60 * 60 * 1000)))
                .equalTo("type", Constants.INCOME)
                .sum("amount")
                .toDouble()

            expense = realm!!.where(Transaction::class.java)
                .greaterThanOrEqualTo("date", calendar.time)
                .lessThan("date", Date(calendar.time.time + (24 * 60 * 60 * 1000)))
                .equalTo("type", Constants.EXPENSE)
                .sum("amount")
                .toDouble()

            total = realm!!.where(Transaction::class.java)
                .greaterThanOrEqualTo("date", calendar.time)
                .lessThan("date", Date(calendar.time.time + (24 * 60 * 60 * 1000)))
                .sum("amount")
                .toDouble()
        } else if (Constants.SELECTED_TAB === Constants.MONTHLY) {
            calendar[Calendar.DAY_OF_MONTH] = 0

            val startTime = calendar.time


            calendar.add(Calendar.MONTH, 1)
            val endTime = calendar.time

            newTransactions = realm!!.where(Transaction::class.java)
                .greaterThanOrEqualTo("date", startTime)
                .lessThan("date", endTime)
                .findAll()

            income = realm!!.where(Transaction::class.java)
                .greaterThanOrEqualTo("date", startTime)
                .lessThan("date", endTime)
                .equalTo("type", Constants.INCOME)
                .sum("amount")
                .toDouble()

            expense = realm!!.where(Transaction::class.java)
                .greaterThanOrEqualTo("date", startTime)
                .lessThan("date", endTime)
                .equalTo("type", Constants.EXPENSE)
                .sum("amount")
                .toDouble()

            total = realm!!.where(Transaction::class.java)
                .greaterThanOrEqualTo("date", startTime)
                .lessThan("date", endTime)
                .sum("amount")
                .toDouble()
        }

        totalIncome.setValue(income)
        totalExpense.setValue(expense)
        totalAmount.setValue(total)
        transactions.setValue(newTransactions)

        //        RealmResults<Transaction> newTransactions = realm.where(Transaction.class)
//                .equalTo("date", calendar.getTime())
//                .findAll();
    }

    fun addTransaction(transaction: Transaction?) {
        realm!!.beginTransaction()
        realm!!.copyToRealmOrUpdate(transaction)
        // some code here
        realm!!.commitTransaction()
    }

    fun deleteTransaction(transaction: Transaction) {
        realm!!.beginTransaction()
        transaction.deleteFromRealm()
        realm!!.commitTransaction()
        getTransactions(calendar)
    }

    fun addTransactions() {
        realm!!.beginTransaction()
        realm!!.copyToRealmOrUpdate(
            Transaction(
                Constants.INCOME,
                "Business",
                "Cash",
                "Some note here",
                Date(),
                500.0,
                Date().time
            )
        )
        realm!!.copyToRealmOrUpdate(
            Transaction(
                Constants.EXPENSE,
                "Investment",
                "Bank",
                "Some note here",
                Date(),
                -900.0,
                Date().time
            )
        )
        realm!!.copyToRealmOrUpdate(
            Transaction(
                Constants.INCOME,
                "Rent",
                "Other",
                "Some note here",
                Date(),
                500.0,
                Date().time
            )
        )
        realm!!.copyToRealmOrUpdate(
            Transaction(
                Constants.INCOME,
                "Business",
                "Card",
                "Some note here",
                Date(),
                500.0,
                Date().time
            )
        )
        // some code here
        realm!!.commitTransaction()
    }

    fun setupDatabase() {
        realm = Realm.getDefaultInstance()
    }
}