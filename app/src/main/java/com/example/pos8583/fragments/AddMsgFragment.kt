package com.example.pos8583.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.navigation.fragment.findNavController
import com.example.pos8583.R
import com.example.pos8583.models.Message
import com.example.pos8583.models.MessagesProvider

class AddMsgFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_msg, container, false)

        val field = view.findViewById<EditText>(R.id.editText_addMsg_editable)
        val button = view.findViewById<Button>(R.id.button_addMsg_add)

        button.setOnClickListener {
            if (field.text.isEmpty()) {
                Toast.makeText(activity, "El mensaje no debe estar vacio", LENGTH_LONG).show()
            } else {
                val messageText = field.text.toString()
                val messageToSave = Message(messageText)
                MessagesProvider.getProvider().addMessage(messageToSave)
                findNavController().navigateUp()
            }
        }
        return view
    }
}