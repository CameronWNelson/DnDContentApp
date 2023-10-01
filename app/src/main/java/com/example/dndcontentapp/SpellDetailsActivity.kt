package com.example.dndcontentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class SpellDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spell_details)
        val bundle: Bundle? = intent.extras
        val nameView = findViewById<TextView>(R.id.nameView)
        val levelView = findViewById<TextView>(R.id.levelView)
        val castTimeView = findViewById<TextView>(R.id.castTimeView)
        val componentsView = findViewById<TextView>(R.id.componentsView)
        val rangeView = findViewById<TextView>(R.id.rangeView)
        val durationView = findViewById<TextView>(R.id.durationView)
        val ritualView = findViewById<TextView>(R.id.ritualView)
        val playerClassView = findViewById<TextView>(R.id.playerClassView)
        val subclassView = findViewById<TextView>(R.id.subclassView)
        val spellTextView = findViewById<TextView>(R.id.spellTextView)
        Log.d("LOG", "Opened new activity")

        val spell = bundle!!.getParcelable<SpellData>("Spell")
        nameView.text = spell!!.name
        levelView.text = spell.levelAndSchoolToString()
        if (spell.concentration) durationView.text = "Duration: Concentration, ${spell.duration}"
        else durationView.text = "Duration: ${spell.duration}"
        castTimeView.text = "Casting Time: ${spell.castTime}"
        rangeView.text = "Range: ${spell.range}"
        componentsView.text = "Components: ${spell.componentToString()}"
        if (spell.ritual) ritualView.text = "Ritual: YES"
        else ritualView.text = "Ritual: NO"
        var playerClassesString = "Classes: "
        for (i in spell.playerClass.indices) {
            if (i != 0) playerClassesString += ", "
            playerClassesString += spell.playerClass[i].name
        }
        playerClassView.text = playerClassesString
        var subclassesString = "Subclasses: "
        for (i in spell.subclass.indices) {
            if (i != 0) subclassesString += ", "
            subclassesString += spell.subclass[i].name
        }
        subclassView.text = subclassesString
        spellTextView.text = spell.spellText
    }
}