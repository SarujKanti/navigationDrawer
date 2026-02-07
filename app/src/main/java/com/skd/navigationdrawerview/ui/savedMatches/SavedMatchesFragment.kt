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
import com.skd.navigationdrawerview.ui.allMatches.AllMatchesViewModel

class SavedMatchesFragment : Fragment() {

    private val savedVM: SavedMatchesViewModel by activityViewModels()
    private val allVM: AllMatchesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_saved_matches, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        val emptyText = view.findViewById<TextView>(R.id.txtEmpty)

        recycler.layoutManager = LinearLayoutManager(requireContext())

        val adapter = SavedMatchesAdapter {
            savedVM.removeMatch(it.id)
        }

        recycler.adapter = adapter

        savedVM.savedMatches.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)

            recycler.visibility =
                if (list.isNullOrEmpty()) View.GONE else View.VISIBLE
            emptyText.visibility =
                if (list.isNullOrEmpty()) View.VISIBLE else View.GONE
        }

        return view
    }
}



