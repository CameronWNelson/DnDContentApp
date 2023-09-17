package com.example.dndcontentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    var allSpells = mutableListOf<SpellData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbManager = getDbManager(this)
        val database = dbManager.openDatabase()

        // create a list of all the spells in the database
        var spellCursor = dbManager.readableDatabase.rawQuery("SELECT * FROM spells", null)
        while (spellCursor.moveToNext()) {
            val name = spellCursor.getString(spellCursor.run { getColumnIndex("name") })
            val level = spellCursor.getInt(spellCursor.run { getColumnIndex("level") })
            val schoolString = spellCursor.getString(spellCursor.run { getColumnIndex("school") })
            var school = School.ERROR
            for (s in School.values()) {
                if (schoolString.compareTo(s.toString(), true) == 0) {
                    school = s
                    break
                }
            }
            val ritual = intToBoolean(spellCursor.getInt(spellCursor.run { getColumnIndex("ritual") }))
            val concentration = intToBoolean(spellCursor.getInt(spellCursor.run { getColumnIndex("concentration") }))
            val verbal = intToBoolean(spellCursor.getInt(spellCursor.run { getColumnIndex("verbal") }))
            val somatic = intToBoolean(spellCursor.getInt(spellCursor.run { getColumnIndex("somatic") }))
            val material = intToBoolean(spellCursor.getInt(spellCursor.run { getColumnIndex("material") }))
            val materialText = spellCursor.getString(spellCursor.run { getColumnIndex("materialText") })
            val component = Component (verbal, somatic, material, materialText)
            val range = spellCursor.getString(spellCursor.run { getColumnIndex("range") })
            val duration = spellCursor.getString(spellCursor.run { getColumnIndex("duration") })
            val castTime = spellCursor.getString(spellCursor.run { getColumnIndex("castTime") })
            val spellText = spellCursor.getString(spellCursor.run { getColumnIndex("spellText") })
            var playerClass = mutableListOf<PlayerClass>()
            val classesString = spellCursor.getString(spellCursor.run { getColumnIndex("classes") })
            for (pc in PlayerClass.values()) {
                if (classesString.contains(pc.toString(), true))
                    playerClass.add(pc)
            }
            var subclass = mutableListOf<Subclass>()
            val subclassString = spellCursor.getString(spellCursor.run { getColumnIndex("subclasses") })
            for (s in Subclass.values()) {
                if (subclassString.contains(s.toString(), true))
                    subclass.add(s)
            }
            allSpells.add(SpellData(name, level, school, ritual, concentration, component, range, duration, castTime, spellText, playerClass, subclass))
        }

//        val alarmButton = findViewById<Button>(R.id.spellButton)
//        alarmButton.setOnClickListener {
//            Log.d("DOGGO", "ALARM pressed")
//            val intent = Intent(this, SpellDetailsActivity::class.java)
//            startActivity(intent)
//        }
    }

    // turn an int of 0 or 1 into a boolean
    fun intToBoolean(i: Int): Boolean {
        if (i == 0) {
            return false
        }
        if (i == 1) {
            return true
        }
        throw IllegalArgumentException("$i cannot be converted to boolean, expected 0 or 1")
    }
}