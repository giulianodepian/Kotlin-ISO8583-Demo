package com.example.pos8583.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pos8583.R
import com.example.pos8583.models.Message
import com.example.pos8583.models.MessagesProvider

class MessagesAdapter: RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message, listener: ((Int)->Unit)? = null, position: Int){
            val messageText = itemView.findViewById<TextView>(R.id.text_list_msg)
            messageText.text = message.text
            messageText.setOnClickListener {
                listener?.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val itemNote = inflator.inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(itemNote)
    }
    override fun getItemCount(): Int {
        return MessagesProvider.getProvider().getMessages().count()
    }

    private var selectedRowListener: ((Int)->Unit)? = null

    fun setOnRowSelected(listener: (Int)->Unit) {
        selectedRowListener = listener
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val noteItem = MessagesProvider.getProvider().get(position)
        if (noteItem != null) {
            holder.bind(noteItem, selectedRowListener, position)
        }
    }
}