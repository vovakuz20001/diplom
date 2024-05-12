package com.example.mycityisacleancity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PrizesAdapter(
    var items: List<PrizeItem>,
    var context: Context,
    var currentScore: Int,
    var btnScoreText: String,
    val username: String,
    val password: String,
    val db: DbHelper
) : RecyclerView.Adapter<PrizesAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val desc: TextView = view.findViewById(R.id.item_list_desc)
        val price: TextView = view.findViewById(R.id.item_list_price)
        val btnChoose: Button = view.findViewById(R.id.item_list_button)
        val scoreText: TextView = view.findViewById(R.id.item_list_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.desc.text = item.desc
        holder.price.text = item.price.toString()
        holder.scoreText.text = btnScoreText
        holder.scoreText.visibility = View.INVISIBLE
        val imageId = context.resources.getIdentifier(
            item.image,
            "drawable",
            context.packageName
        )
        holder.image.setImageResource(imageId)

        holder.btnChoose.setOnClickListener {
            if (currentScore >= item.price) {
                currentScore -= item.price
                btnScoreText = currentScore.toString()
                db.updateScores(username, password, currentScore)

                (context as PrizesActivity).updateScoreText(btnScoreText)


                db.addMyPrize(username, item.title, item.price)


                val intent = Intent(context, MyPrizeActivity::class.java)
                intent.putExtra("prizeTitle", item.title)
                intent.putExtra("prizePrice", item.price)
                intent.putExtra("username", username)
                intent.putExtra("password", password)
                context.startActivity(intent)

                notifyDataSetChanged()

                Toast.makeText(context, "Вы приобрели ${item.title}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Недостаточно баллов для покупки", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
