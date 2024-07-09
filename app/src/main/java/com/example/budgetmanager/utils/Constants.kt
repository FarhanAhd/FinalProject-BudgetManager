package com.example.budgetmanager.utils

import com.example.budgetmanager.R
import com.example.budgetmanager.models.Category

object Constants {
    var INCOME: String = "INCOME"
    var EXPENSE: String = "EXPENSE"

    var categories: ArrayList<Category>? = null

    var DAILY: Int = 0
    var MONTHLY: Int = 1
    var CALENDAR: Int = 2
    var SUMMARY: Int = 3
    var NOTES: Int = 4

    var SELECTED_TAB: Int = 0

    open fun setCategories() {
        categories = ArrayList<Category>()
        categories!!.add(Category("Salary", R.drawable.ic_salary, R.color.category1))
        categories!!.add(Category("Business", R.drawable.ic_business, R.color.category2))
        categories!!.add(Category("Investment", R.drawable.ic_investment, R.color.category3))
        categories!!.add(Category("Loan", R.drawable.ic_loan, R.color.category4))
        categories!!.add(Category("Rent", R.drawable.ic_rent, R.color.category5))
        categories!!.add(Category("Other", R.drawable.ic_other, R.color.category6))
    }

    fun getCategoryDetails(categoryName: String?): Category? {
        for (cat in categories!!) {
            if (cat.getCategoryName().equals(categoryName)) {
                return cat
            }
        }
        return null
    }

    fun getAccountsColor(accountName: String?): Int {
        return when (accountName) {
            "Bank" -> R.color.bank_color
            "Cash" -> R.color.cash_color
            "Card" -> R.color.card_color
            else -> R.color.default_color
        }
    }
}