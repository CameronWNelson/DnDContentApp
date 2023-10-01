package com.example.dndcontentapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class SpellRecyclerViewAdapter(private val spellList : ArrayList<SpellData>): RecyclerView.Adapter<SpellRecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_spell_row, parent, false)
        return MyViewHolder(itemView)
    }

    // Assign the data for each spell to the appropriate view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = spellList[position]
        holder.nameView.setText(currentItem.name)
        holder.detailsView.setText(currentItem.levelAndSchoolToString())
        holder.spell = currentItem
    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    // Display the data for each spell
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nameView : TextView = itemView.findViewById(R.id.spellName)
        val detailsView : TextView = itemView.findViewById(R.id.spellDetails)
        val spellRow : LinearLayout = itemView.findViewById(R.id.spellRow)
        lateinit var spell: SpellData

        init {
            spellRow.setOnClickListener {
                val intent = Intent(it.context, SpellDetailsActivity::class.java).apply {
                    putExtra("Spell", spell)
                }
                it.context.startActivity(intent)
            }
        }
    }
}