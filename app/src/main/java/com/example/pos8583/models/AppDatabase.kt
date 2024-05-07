package com.example.pos8583.models

import androidx.room.Database
import androidx.room.RoomDatabase

//Una entidad por cada tabla
@Database(entities = [Message::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    //Una funcion por cada Dao (o sea, por cada tabla)
    abstract fun messagesDao(): MessagesDao
}