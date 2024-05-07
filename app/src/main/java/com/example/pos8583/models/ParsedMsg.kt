package com.example.pos8583.models

data class ParsedMsg(
    val cardNumber: String,
    val amountNumber: String,
    val dateTime: String,
    val cardCompany: String
)
