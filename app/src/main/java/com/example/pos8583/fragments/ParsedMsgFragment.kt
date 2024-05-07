package com.example.pos8583.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pos8583.R
import com.example.pos8583.viewmodels.ParsedMsgViewModel

class ParsedMsgFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_parsed_msg, container, false)

        val cardNumberTextView = view.findViewById<TextView>(R.id.text_parsedMsg_cardNum)
        val tranAmountTextView = view.findViewById<TextView>(R.id.text_parsedMsg_amount)
        val dateTimeTextView = view.findViewById<TextView>(R.id.text_parsedMsg_dateTime)
        val cardCompanyTextView = view.findViewById<TextView>(R.id.text_parsedMsg_cardName)

        val viewModel = ViewModelProvider(requireActivity()).get(ParsedMsgViewModel::class.java)

        viewModel.getModel().observe(requireActivity(), Observer {model ->
            cardNumberTextView.text = model.cardNumber
            tranAmountTextView.text = model.amountNumber
            dateTimeTextView.text = model.dateTime
            cardCompanyTextView.text = model.cardCompany
        })

        return view
    }
}