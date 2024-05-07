package com.example.pos8583.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Messages")
data class Message(var text: String) {
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}