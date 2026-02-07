package com.skd.navigationdrawerview.ui.allMatches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skd.navigationdrawerview.R
import com.skd.navigationdrawerview.data.remote.Venue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchesAdapter(
    private val onStarClick: (Venue) -> Unit
) : ListAdapter<Venue, MatchesAdapter.VH>(Diff()) {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtName)
        val sub: TextView = view.findViewById(R.id.txtSubName)
        val star: ImageView = view.findViewById(R.id.imgStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.sub.text = item.location.country
        holder.star.setImageResource(R.drawable.ic_star_border)

        holder.star.setOnClickListener {
            onStarClick(item)
        }
    }

    class Diff : DiffUtil.ItemCallback<Venue>() {
        override fun areItemsTheSame(a: Venue, b: Venue) = a.id == b.id
        override fun areContentsTheSame(a: Venue, b: Venue) = a == b
    }
}

