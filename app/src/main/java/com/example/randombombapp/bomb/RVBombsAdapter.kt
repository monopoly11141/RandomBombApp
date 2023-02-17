package com.example.randombombapp.bomb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randombombapp.R

class RVBombsAdapter(val imagesString: MutableList<String>) : RecyclerView.Adapter<RVBombsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVBombsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_rvbomb_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVBombsAdapter.ViewHolder, position: Int) {
        holder.bindBombs(imagesString[position])
    }

    override fun getItemCount(): Int {
        return imagesString.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindBombs(imageString: String) {
            val imgBomb = itemView.findViewById<ImageView>(R.id.imgBomb)
            imgBomb.setImageResource(R.drawable.bomb);
        }
    }
}