package com.example.pos8583.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MessagesDao {
    @Query("SELECT * FROM Messages")
    fun getAll(): List<Message>

    @Query("SELECT * FROM Messages WHERE uid = :id LIMIT 1")
    fun getById(id: Int): Message

    @Insert
    fun insert(vararg message: Message)

    @Delete
    fun delete(message: Message)

    @Query("DELETE FROM Messages")
    fun deleteAll()

    @Update
    fun update(message: Message)
}