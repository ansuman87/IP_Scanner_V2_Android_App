package tech.podolak.lukas.ip_scanner_v2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RvAdapter(private val rvList: List<RvItem>) : RecyclerView.Adapter<RvAdapter.RvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)

        return RvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        val currentItem = rvList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.text1.text = currentItem.text1
        holder.text2.text = currentItem.text2
    }

    override fun getItemCount() = rvList.size

    inner class RvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView
        val text1: TextView
        val text2: TextView

        init {
            imageView = itemView.findViewById(R.id.image_view)
            text1 = itemView.findViewById(R.id.text_view_1)
            text2 = itemView.findViewById(R.id.text_view_2)
        }
    }
}