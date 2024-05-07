package com.example.pos8583.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pos8583.API
import com.example.pos8583.R
import com.example.pos8583.adapters.MessagesAdapter
import com.example.pos8583.models.MessagesProvider
import com.example.pos8583.viewmodels.ParsedMsgViewModel

class ListMsgFragment : Fragment() {

    companion object {
        const val KEY_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_msg, container, false)

        val optionType = arguments?.getInt(HomeFragment.KEY_OPTION)

        //RecyclerView Configuration
        val messagesListRecyclerView = view.findViewById<RecyclerView>(R.id.recycler_list_msgs)
        messagesListRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        val adapter = MessagesAdapter()
        messagesListRecyclerView.adapter = adapter

        when (optionType) {
            HomeFragment.LIST_OPTION -> {
                adapter.setOnRowSelected { position ->
                    val bundle = Bundle()
                    bundle.putInt(KEY_POSITION, position)
                    findNavController().navigate(R.id.action_listMsgFragment_to_messageFragment, bundle)
                }
            }
            HomeFragment.DELETE_OPTION -> {
                adapter.setOnRowSelected { position ->
                    val deleteAlert = AlertDialog.Builder(requireContext())
                    deleteAlert.setTitle("Eliminar Mensaje")
                        .setMessage("Estas seguro que quieres eliminar mensaje?")
                    deleteAlert.setPositiveButton("Si") {_, _ ->
                        MessagesProvider.getProvider().deleteMessage(position)
                    }
                    deleteAlert.setNegativeButton("No") {_, _ ->

                    }

                    deleteAlert.show()
                }
            }
            HomeFragment.PARSE_OPTION -> {
                val viewModel = ViewModelProvider(requireActivity()).get(ParsedMsgViewModel::class.java)
                adapter.setOnRowSelected { position ->
                    val parseResult = viewModel.parseMessage(MessagesProvider.getProvider().get(position)?.text!!)
                    if (parseResult != API.CODE_OK) {
                        var errorMessage = ""
                        when(parseResult) {
                            API.CODE_BAD_MSG -> errorMessage = "Mensaje Invalido"
                            API.CODE_BAD_DIGIT -> errorMessage = "Digito de verificacion erroneo"
                            API.CODE_ERR -> errorMessage = "Error No Especificado"
                            API.CODE_UNK_CARD -> errorMessage = "Tarjeta Desconocida"
                        }
                        AlertDialog.Builder(requireContext())
                            .setTitle("Error")
                            .setMessage(errorMessage)
                            .show()
                    } else {
                        findNavController().navigate(R.id.action_listMsgFragment_to_parsedMsgFragment)
                    }
                }
            }
        }

        MessagesProvider.getProvider().registerListener {
            adapter.notifyDataSetChanged()
        }

        return view
    }
}