package com.example.pos8583.models

import androidx.room.Room
import com.example.pos8583.App

class MessagesProvider {
    companion object {
        private val _instance = MessagesProvider()
        fun getProvider(): MessagesProvider {
            return _instance
        }
    }

    private var messages = mutableListOf<Message>()
    private var db: AppDatabase = Room.databaseBuilder(
        App.context,
        AppDatabase::class.java, "POS"
    ).allowMainThreadQueries().build()

    init {
        messages = db.messagesDao().getAll().toMutableList()
    }

    private val listeners = mutableListOf<()->Unit>()
    fun registerListener(listener: ()->Unit){
        listeners.add(listener)
    }

    fun addMessage(message: Message) {
        messages.add(message)
        listeners.forEach { it.invoke() }
        db.messagesDao().insert(message)
    }

    fun deleteMessage(pos: Int) {
        val message = messages[pos]
        messages.remove(message)
        listeners.forEach { it.invoke() }
        db.messagesDao().delete(message)
    }

    fun getMessages(): MutableList<Message> {
        return messages
    }

    fun get(pos: Int): Message? {
        if (pos < 0) {
            return null
        }
        return messages[pos]
    }
}