package com.example.sshop_sneakershop_admin.Account.models

data class Account(
    var id: String,
    var fullName: String?,
    var email: String,
    var address: String?,
    var phone: String?,
    var gender: String?,
    var dob: String?,
    var avatar: String?,
    var status: Boolean,
    var payments: ArrayList<Payment>?,
    var numOfReview: Int = 0
) {
    constructor() : this("", null, "", null, null, null, null, null, true, null)
    constructor(
        id: String,
        fullName: String,
        email: String,
        status: Boolean
    ) : this(id, fullName, email, null, null, null, null, null, status, null)

    constructor(email: String) : this("", null, email, null, null, null, null, null, true, null)
    constructor(
        id: String,
        fullName: String?,
        email: String,
        address: String?,
        phone: String?,
        gender: String?,
        dob: String?,
        avatar: String?,
        status: Boolean
    ) : this(id, fullName, email, address, phone, gender, dob, avatar, status, null)

}