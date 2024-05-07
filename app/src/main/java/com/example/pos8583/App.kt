package com.example.pos8583

import android.app.Application
import android.content.Context
import android.util.Log

class App: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        context = applicationContext
        super.onCreate()
    }
}