package com.example.dndcontentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var spellRecyclerView : RecyclerView
    private lateinit var allSpells : ArrayList<SpellData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbManager = getDbManager(this)
        val database = dbManager.openDatabase()
        allSpells = ArrayList<SpellData>()

        initializeSpellData(allSpells)

        spellRecyclerView = findViewById(R.id.SpellRecyclerView)
        spellRecyclerView.layoutManager = LinearLayoutManager(this)
        spellRecyclerView.adapter = SpellRecyclerViewAdapter(allSpells)
    }

    // create a list of all the spells in the database
    private fun initializeSpellData(spells: ArrayList<SpellData>) {
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
            val ritual = ExtraUtilities.intToBool(spellCursor.getInt(spellCursor.run { getColumnIndex("ritual") }))
            val concentration = ExtraUtilities.intToBool(spellCursor.getInt(spellCursor.run { getColumnIndex("concentration") }))
            val verbal = ExtraUtilities.intToBool(spellCursor.getInt(spellCursor.run { getColumnIndex("verbal") }))
            val somatic = ExtraUtilities.intToBool(spellCursor.getInt(spellCursor.run { getColumnIndex("somatic") }))
            val material = ExtraUtilities.intToBool(spellCursor.getInt(spellCursor.run { getColumnIndex("material") }))
            val materialText = spellCursor.getString(spellCursor.run { getColumnIndex("materialText") })
            val range = spellCursor.getString(spellCursor.run { getColumnIndex("range") })
            val duration = spellCursor.getString(spellCursor.run { getColumnIndex("duration") })
            val castTime = spellCursor.getString(spellCursor.run { getColumnIndex("castTime") })
            val spellText = spellCursor.getString(spellCursor.run { getColumnIndex("spellText") })
            var playerClass = ArrayList<PlayerClass>()
            val classesString = spellCursor.getString(spellCursor.run { getColumnIndex("classes") })
            for (pc in PlayerClass.values()) {
                if (classesString.contains(pc.toString(), true))
                    playerClass.add(pc)
            }
            var subclass = ArrayList<Subclass>()
            val subclassString = spellCursor.getString(spellCursor.run { getColumnIndex("subclasses") })
            for (s in Subclass.values()) {
                if (subclassString.contains(s.toString(), true))
                    subclass.add(s)
            }
            spells.add(SpellData(name, level, school, ritual, concentration, verbal, somatic, material, materialText, range, duration, castTime, spellText, playerClass, subclass))
        }
    }
}