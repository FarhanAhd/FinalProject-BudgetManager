package com.example.budgetmanager.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date


open class Transaction : RealmObject {
    var type: String? = null
    var category: String? = null
    var account: String? = null
    var note: String? = null
    var date: Date? = null
    var amount: Double = 0.0

    @PrimaryKey
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

    fun getType(): String {
        return type!!
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getCategory(): String {
        return category!!
    }

    fun setCategory(category: String?) {
        this.category = category
    }

    fun getAccount(): String {
        return account!!
    }

    fun setAccount(account: String?) {
        this.account = account
    }

    fun getNote(): String {
        return note!!
    }

    fun setNote(note: String?) {
        this.note = note
    }

    fun getDate(): Date {
        return date!!
    }

    fun setDate(date: Date?) {
        this.date = date
    }

    fun getAmount(): Double {
        return amount
    }

    fun setAmount(amount: Double) {
        this.amount = amount
    }

    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }
}