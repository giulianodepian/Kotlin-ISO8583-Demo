package com.example.pos8583

import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Date

class ParsedMessage{
    var statusCode: Number = 0
    var cardNumberLen: Number = 0
    var cardNumber: String = ""
    var tranAmount: Number = 0
    var dateAndTime: String = ""
    var company: String = ""
}

class API {
    companion object {
        const val CODE_OK = 0
        const val CODE_BAD_MSG = -1
        const val CODE_BAD_DIGIT = -2
        const val CODE_ERR = -3
        const val CODE_UNK_CARD = -4
        const val fixedBitmap: String = "5200000000000000"

        fun parseMessage(messageToParse: String?): ParsedMessage{
            var offset = 0
            val resultMessage = ParsedMessage()
            if (messageToParse == null) {
                resultMessage.statusCode = CODE_ERR
                return resultMessage
            }
            val messageLen = messageToParse.length

            //Get PAN and verify check digit
            if (fixedBitmap[0].digitToInt() and 0x04 != 0) {
                if (offset + 2 > messageLen) {
                    resultMessage.statusCode = CODE_BAD_MSG
                    return resultMessage
                }

                val cardNumberLenDigitOne = messageToParse[0]
                val cardNumberLenDigitTwo = messageToParse[1]
                val cardNumberLenNmb =
                    try {
                        "$cardNumberLenDigitOne$cardNumberLenDigitTwo".toInt()
                    }
                    catch (e: NumberFormatException){
                        -1
                    }
                if (cardNumberLenNmb == -1 ) {
                    resultMessage.statusCode = CODE_BAD_MSG
                    return resultMessage
                }
                resultMessage.cardNumberLen = cardNumberLenNmb
                offset += 2

                if (offset + cardNumberLenNmb > messageLen) {
                    resultMessage.statusCode = CODE_BAD_MSG
                    return resultMessage
                }

                resultMessage.cardNumber =
                    messageToParse.substring(offset, offset + cardNumberLenNmb)
                if (!luhnAlgorithm(resultMessage.cardNumber)) {
                    resultMessage.statusCode = CODE_BAD_DIGIT
                    return resultMessage
                }
                val cardBin = resultMessage.cardNumber.substring(0, 6)

                val input = App.context.assets.open("cardRange.dat").bufferedReader()
                var fileLine = input.readLine()
                while (fileLine != null) {
                    val lowRange = fileLine.substring(0, 6)
                    val highRange = fileLine.substring(6, 12)
                    if (cardBin in lowRange..highRange) {
                        resultMessage.company = fileLine.substring(12)
                        break;
                    }
                    fileLine = input.readLine()
                }
                if (resultMessage.company.isEmpty()) {
                    resultMessage.statusCode = CODE_UNK_CARD
                    return resultMessage
                }
                offset += cardNumberLenNmb
            }

            //Get Amount
            if (fixedBitmap[0].digitToInt() and 0x04 != 0) {
                if (offset + 12 > messageLen) {
                    resultMessage.statusCode = CODE_BAD_MSG
                    return resultMessage
                }
                resultMessage.tranAmount  = try {
                   messageToParse.substring(offset, offset + 12).toInt()
                }
                catch (e: NumberFormatException) {
                    -1
                }
                if (resultMessage.tranAmount == -1) {
                    resultMessage.statusCode = CODE_BAD_MSG
                    return resultMessage
                }
                offset += 12
            }

            //Get date and time
            if (fixedBitmap[1].digitToInt() and 0x02 != 0) {
                if (offset + 10 > messageLen) {
                    resultMessage.statusCode = CODE_BAD_MSG
                    return resultMessage
                }
                val dateAndTime = messageToParse.substring(offset, offset + 10)
                resultMessage.dateAndTime = dateAndTime
            }

            return resultMessage
        }

        private fun luhnAlgorithm(cardNumber: String): Boolean {
            var finalCheck = 0
            val checkDigit = cardNumber[cardNumber.length - 1].digitToInt()
            val parity = (cardNumber.length).mod(2)
            for (i in 0..cardNumber.length - 1) {
                if (i.mod(2) != parity) {
                    finalCheck += cardNumber[i].digitToInt()
                } else if (cardNumber[i].digitToInt() > 4) {
                    finalCheck += cardNumber[i].digitToInt() * 2 - 9
                } else {
                    finalCheck += cardNumber[i].digitToInt() * 2
                }
            }
            return 0 == finalCheck.mod(10)
        }
    }
}