package com.example.dndcontentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbManager = getDbManager(this)
        val database = dbManager.openDatabase()

        val alarmButton = findViewById<Button>(R.id.spellButton)
        alarmButton.setOnClickListener {
            Log.d("DOGGO", "ALARM pressed")
            val intent = Intent(this, SpellDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}