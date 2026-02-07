package com.skd.navigationdrawerview.ui.savedMatches

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skd.navigationdrawerview.R
import com.skd.navigationdrawerview.data.local.MatchEntity

class SavedMatchesAdapter(
    private val onStarClick: (MatchEntity) -> Unit
) : ListAdapter<MatchEntity, SavedMatchesAdapter.VH>(Diff()) {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtName)
        val country: TextView = view.findViewById(R.id.txtSubName)
        val star: ImageView = view.findViewById(R.id.imgStar)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.country.text = item.country
        holder.star.setImageResource(R.drawable.ic_star_filled)
        holder.star.setOnClickListener { onStarClick(item) }
    }

    class Diff : DiffUtil.ItemCallback<MatchEntity>() {
        override fun areItemsTheSame(a: MatchEntity, b: MatchEntity) = a.id == b.id
        override fun areContentsTheSame(a: MatchEntity, b: MatchEntity) = a == b
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)
        return VH(view)
    }
}
