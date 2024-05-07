package com.example.pos8583.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.pos8583.R

class HomeFragment : Fragment() {

    companion object {
        const val LIST_OPTION = 1
        const val DELETE_OPTION = 2
        const val PARSE_OPTION = 3
        const val KEY_OPTION = "option"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val listMessagesButton = view.findViewById<TextView>(R.id.text_home_listMsg)
        val addMessageButton = view.findViewById<TextView>(R.id.text_home_addMsg)
        val deleteMessageButton = view.findViewById<TextView>(R.id.text_home_deleteMsg)
        val parseMessageButton = view.findViewById<TextView>(R.id.text_home_parseMsg)

        val bundle = Bundle()

        listMessagesButton.setOnClickListener {
            bundle.putInt(KEY_OPTION, LIST_OPTION)
            findNavController().navigate(R.id.action_homeFragment_to_listMsgFragment, bundle)
        }

        addMessageButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addMsgFragment)
        }

        deleteMessageButton.setOnClickListener {
            bundle.putInt(KEY_OPTION, DELETE_OPTION)
            findNavController().navigate(R.id.action_homeFragment_to_listMsgFragment, bundle)
        }

        parseMessageButton.setOnClickListener {
            bundle.putInt(KEY_OPTION, PARSE_OPTION)
            findNavController().navigate(R.id.action_homeFragment_to_listMsgFragment, bundle)
        }

        return view
    }

}