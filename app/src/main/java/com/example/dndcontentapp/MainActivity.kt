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
        // playerClass is set to null and commented out, fix
        val alarm = Spell("Alarm", 1, "1 minute", "30 feet", "20-foot cube",
            Component(true, true, true, "a tiny bell and a piece of fine silver wire"),
                "8 hours", School.ABJURATION, false, true, /*null,*/
            "You set an alarm against unwanted intrusion. Choose a door, a window, or an area within range that is no larger than a 20-foot cube. Until the spell ends, an alarm alerts you whenever a Tiny or larger creature touches or enters the warded area. When you cast the spell, you can designate creatures that won't set off the alarm. You also choose whether the alarm is mental or audible.\n\n" +
                    "A mental alarm alerts you with a ping in your mind if you are within 1 mile of the warded area. This ping awakens you if you are sleeping.\n\n" +
                    "An audible alarm produces the sound of a hand bell for 10 seconds within 60 feet.")
        val alarmButton = findViewById<Button>(R.id.spellButton)
        alarmButton.setOnClickListener {
            Log.d("TAG", "ALARM pressed")
            val intent = Intent(this, SpellDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}