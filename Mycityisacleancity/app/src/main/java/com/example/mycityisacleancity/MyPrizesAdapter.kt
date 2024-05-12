package com.example.mycityisacleancity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyPrizesAdapter(
    private val myPrizesList: List<MyPrizeItem>,
    private val context: Context
) : RecyclerView.Adapter<MyPrizesAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_prize_title)
        val pointTextView: TextView = itemView.findViewById(R.id.item_prize_point)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_prize, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myPrizesList[position]
        holder.titleTextView.text = currentItem.title
        holder.pointTextView.text = currentItem.point
    }

    override fun getItemCount(): Int {
        return myPrizesList.size
    }
}
