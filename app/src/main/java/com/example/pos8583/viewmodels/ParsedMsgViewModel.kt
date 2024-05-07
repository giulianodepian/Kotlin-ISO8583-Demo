package com.example.pos8583.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pos8583.API
import com.example.pos8583.R
import com.example.pos8583.fragments.ParsedMsgFragment
import com.example.pos8583.models.ParsedMsg

class ParsedMsgViewModel: ViewModel() {
    private var model = MutableLiveData<ParsedMsg>()

    fun getModel(): LiveData<ParsedMsg> {
        return model
    }

    fun parseMessage(MessageToParse: String): Number {
        val parsedMessage = API.parseMessage(MessageToParse)

        if (parsedMessage.statusCode == API.CODE_OK) {

            var amountNumber = parsedMessage.tranAmount.toString()
            val normal = amountNumber?.substring(0, amountNumber.length - 2)
            val cents = amountNumber?.substring(amountNumber.length - 2)
            amountNumber = "$${normal},${cents}"

            val dateTime = parsedMessage.dateAndTime
            val month = dateTime?.substring(0, 2)
            val day = dateTime?.substring(2, 4)
            val hour = dateTime?.substring(4, 6)
            val minute = dateTime?.substring(6, 8)
            val second = dateTime?.substring(8, 10)
            val dateTimeFormat = "${month}/${day} ${hour}:${minute}:${second}"

            model.postValue(
                ParsedMsg(
                    cardNumber = parsedMessage.cardNumber,
                    cardCompany = parsedMessage.company,
                    amountNumber = amountNumber,
                    dateTime = dateTimeFormat
                )
            )
        }

        return parsedMessage.statusCode
    }
}