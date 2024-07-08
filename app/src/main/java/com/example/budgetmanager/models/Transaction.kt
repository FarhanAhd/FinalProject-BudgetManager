package com.example.budgetmanager.models

import java.util.Date

class Transaction {
    var type: String? = null
    var category: String? = null
    var account: String? = null
    var note: String? = null
    var date: Date? = null
    var amount: Double = 0.0

    var id: Long = 0

    constructor()

    constructor(
        type: String?,
        category: String?,
        account: String?,
        note: String?,
        date: Date?,
        amount: Double,
        id: Long
    ) {
        this.type = type
        this.category = category
        this.account = account
        this.note = note
        this.date = date
        this.amount = amount
        this.id = id
    }
}