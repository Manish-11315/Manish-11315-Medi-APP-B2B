package com.mysecondapp.mediadmin.model

data class UserDataModelItem(
    val Id: Int,
    val address: String,
    val block_status: Int,
    val date_of_creation: String,
    val email: String,
    val is_approved: Int,
    val name: String,
    val password: String,
    val phone_number: String,
    val pincode: String,
    val user_id: String
)