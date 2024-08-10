package com.thecodeproject.`in`.safezone.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.thecodeproject.`in`.safezone.R
import com.thecodeproject.`in`.safezone.models.Articles
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NewsAdapter (private val context: Context, private val articlesList: List<Articles>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_title)
        val shortDesc: TextView = view.findViewById(R.id.tv_shortDesc)
        val cvArticle: CardView = view.findViewById(R.id.cv_article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articlesList[position]
        holder.title.text = article.title
        holder.shortDesc.text = article.description
        holder.cvArticle.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int = articlesList.size

    private fun convertTime(timeInMillis: Long): String {
        val date = Date(timeInMillis)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}
