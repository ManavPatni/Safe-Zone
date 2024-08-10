package com.thecodeproject.`in`.safezone.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thecodeproject.`in`.safezone.R
import com.thecodeproject.`in`.safezone.models.Feature
import java.text.SimpleDateFormat
import java.util.*

class EarthquakeAdapter(private val earthquakeList: List<Feature>) : RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder>() {

    class EarthquakeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeTextView: TextView = view.findViewById(R.id.placeTextView)
        val magnitudeTextView: TextView = view.findViewById(R.id.magnitudeTextView)
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val timelineMarker: View = view.findViewById(R.id.timelineMarker)
        val timelineLine: View = view.findViewById(R.id.timelineLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthquakeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_earthquake, parent, false)
        return EarthquakeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EarthquakeViewHolder, position: Int) {
        val feature = earthquakeList[position]
        val color = getMagnitudeColor(feature.properties.mag)
        holder.placeTextView.text = feature.properties.place
        holder.magnitudeTextView.text = "Magnitude: ${feature.properties.mag}"
        holder.timeTextView.text = "Time: ${convertTime(feature.properties.time)}"
        holder.timelineMarker.background = getColoredDrawable(24, color)  // Dot size and color
        holder.timelineLine.setBackgroundColor(color)
    }

    override fun getItemCount(): Int = earthquakeList.size

    private fun getColoredDrawable(size: Int, color: Int): Drawable {
        val shape = ShapeDrawable(OvalShape())
        shape.intrinsicHeight = size
        shape.intrinsicWidth = size
        shape.paint.color = color
        return shape
    }

    private fun getMagnitudeColor(magnitude: Double): Int {
        return when {
            magnitude < 2.0 -> Color.GREEN
            magnitude < 4.0 -> Color.YELLOW
            magnitude < 6.0 -> Color.RED
            else -> Color.RED
        }
    }

    private fun convertTime(timeInMillis: Long): String {
        val date = Date(timeInMillis)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}
