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
            spells.add(SpellData.createFromCursor(spellCursor))
        }
    }
}