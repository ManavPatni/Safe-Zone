package com.thecodeproject.`in`.safezone.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.thecodeproject.`in`.safezone.R
import com.thecodeproject.`in`.safezone.models.Feature
import com.bumptech.glide.Glide
import com.thecodeproject.`in`.safezone.models.FFRFeature

class ForestFireAdapter(private val context: Context, private val forestFireList: List<FFRFeature>) : RecyclerView.Adapter<ForestFireAdapter.ForestFireViewHolder>() {

    class ForestFireViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llFireDetails: ConstraintLayout = view.findViewById(R.id.cl_fireDetail)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val severityTextView: TextView = view.findViewById(R.id.severityTextView)
        val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForestFireViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forest_fire, parent, false)
        return ForestFireViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForestFireViewHolder, position: Int) {
        val feature = forestFireList[position]
        holder.nameTextView.text = feature.properties.name
        holder.descriptionTextView.text = feature.properties.description
        holder.severityTextView.text = feature.properties.severitydata.severitytext
        Glide.with(holder.itemView.context).load(feature.properties.icon).into(holder.iconImageView)
        holder.llFireDetails.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(feature.properties.url.report))
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int = forestFireList.size
}
