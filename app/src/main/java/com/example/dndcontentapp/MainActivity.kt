package com.example.dndcontentapp

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var spellRecyclerView : RecyclerView
    private lateinit var searchView : androidx.appcompat.widget.SearchView
    private lateinit var allSpells : ArrayList<SpellData>
    private lateinit var currentSpells : ArrayList<SpellData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbManager = getDbManager(this)
        val database = dbManager.openDatabase()
        allSpells = ArrayList()

        initializeAllSpellData(allSpells)
        currentSpells = allSpells

        spellRecyclerView = findViewById(R.id.SpellRecyclerView)
        spellRecyclerView.layoutManager = LinearLayoutManager(this)
        spellRecyclerView.adapter = SpellRecyclerViewAdapter(currentSpells)

        searchView = findViewById(R.id.SearchView)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != "") {
                    val cursor = dbManager.readableDatabase.rawQuery("SELECT * FROM spells WHERE name LIKE \'%$query%\'", null)
                    currentSpells.clear()
                    initializeSpellData(currentSpells, cursor)
                }
                else {
                    currentSpells = allSpells
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    // create a list of all the spells in the database
    private fun initializeAllSpellData(spells: ArrayList<SpellData>) {
        val cursor = dbManager.readableDatabase.rawQuery("SELECT * FROM spells", null)
        initializeSpellData(spells, cursor)
    }

    private fun initializeSpellData(spells: ArrayList<SpellData>, cursor: Cursor) {
        while (cursor.moveToNext()) {
            spells.add(SpellData.createFromCursor(cursor))
        }
    }
}