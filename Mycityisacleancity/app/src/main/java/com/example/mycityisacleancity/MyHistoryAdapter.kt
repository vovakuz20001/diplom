package com.example.mycityisacleancity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyHistoryAdapter(
    private val historyList: List<MyHistoryItem>,
    private val context: Context
) : RecyclerView.Adapter<MyHistoryAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressTextView: TextView = itemView.findViewById(R.id.item_history_address)
        val typeTextView: TextView = itemView.findViewById(R.id.item_history_status)
        val pointTextView: TextView = itemView.findViewById(R.id.item_history_point)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_history, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = historyList[position]
        holder.addressTextView.text = currentItem.address
        holder.typeTextView.text = currentItem.type
        holder.pointTextView.text = currentItem.point.toString()
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}
