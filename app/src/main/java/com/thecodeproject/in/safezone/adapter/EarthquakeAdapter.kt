package com.thecodeproject.`in`.safezone.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thecodeproject.`in`.safezone.R
import com.thecodeproject.`in`.safezone.models.Feature
import java.text.SimpleDateFormat
import java.util.*

class EarthquakeAdapter(private val context: Context, private val earthquakeList: List<Feature>) : RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder>() {

    class EarthquakeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeTextView: TextView = view.findViewById(R.id.placeTextView)
        val magnitudeTextView: TextView = view.findViewById(R.id.magnitudeTextView)
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val timelineMarker: View = view.findViewById(R.id.timelineMarker)
        val timelineLine: View = view.findViewById(R.id.timelineLine)
        val llDetail: LinearLayout = view.findViewById(R.id.ll_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthquakeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_earthquake, parent, false)
        return EarthquakeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EarthquakeViewHolder, position: Int) {
        val feature = earthquakeList[position]
        val magnitude = feature.properties.severitydata.severity
        val place = feature.properties.name // Assuming `name` is the place

        val color = getMagnitudeColor(magnitude)
        holder.placeTextView.text = place
        holder.magnitudeTextView.text = "Magnitude: $magnitude"
        holder.timeTextView.text = "Time: ${convertTime(feature.properties.fromdate)}"
        holder.timelineMarker.background = getColoredDrawable(24, color)  // Dot size and color
        holder.timelineLine.setBackgroundColor(color)

        holder.llDetail.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(feature.properties.url.report))
            context.startActivity(browserIntent)
        }
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
            magnitude < 6.0 -> Color.parseColor("#FFA500")
            else -> Color.RED
        }
    }

    private fun convertTime(timeInMillis: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return try {
            val date = format.parse(timeInMillis)
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            timeInMillis // Return original string if parsing fails
        }
    }
}
