package com.example.dndcontentapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        if (currentItem.level == 0) {
            holder.detailsView.setText("${currentItem.schoolToString(true)} ${currentItem.levelToString()}")
        }
        else {
            holder.detailsView.setText("${currentItem.levelToString()} ${currentItem.schoolToString(false)}")
        }
    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    // Display the data for each spell
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nameView : TextView = itemView.findViewById(R.id.spellName)
        val detailsView : TextView = itemView.findViewById(R.id.spellDetails)
    }
}