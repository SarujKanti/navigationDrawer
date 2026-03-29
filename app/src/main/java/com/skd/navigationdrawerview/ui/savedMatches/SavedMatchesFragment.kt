package com.skd.navigationdrawerview.ui.savedMatches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skd.navigationdrawerview.R

class SavedMatchesFragment : Fragment() {

    private val savedVM: SavedMatchesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_saved_matches, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        val emptyText = view.findViewById<TextView>(R.id.txtEmpty)

        recycler.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SavedMatchesAdapter { item ->
            savedVM.removeMatch(item.id)
        }

        recycler.adapter = adapter

        savedVM.savedMatches.observe(viewLifecycleOwner) { list ->

            adapter.submitList(list)

            if (list.isNullOrEmpty()) {
                recycler.visibility = View.GONE
                emptyText.visibility = View.VISIBLE
            } else {
                recycler.visibility = View.VISIBLE
                emptyText.visibility = View.GONE
            }
        }


        return view
    }
}




