package com.example.pos8583.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pos8583.R
import com.example.pos8583.models.MessagesProvider

class MessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        val messageIndex = arguments?.getInt(ListMsgFragment.KEY_POSITION)

        if (messageIndex == null) {

        }

        val message =MessagesProvider.getProvider().get(messageIndex!!)

        val textView = view.findViewById<TextView>(R.id.text_message_text)
        textView.text = message?.text
        return view
    }
}