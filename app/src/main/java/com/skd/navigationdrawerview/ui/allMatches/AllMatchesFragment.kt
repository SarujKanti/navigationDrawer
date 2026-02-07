package com.skd.navigationdrawerview.ui.allMatches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skd.navigationdrawerview.R

class AllMatchesFragment : Fragment() {

    private val viewModel: AllMatchesViewModel by activityViewModels()
    private lateinit var adapter: MatchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_all_matches, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
        val progress = view.findViewById<View>(R.id.progressBar)

        recycler.layoutManager = LinearLayoutManager(requireContext())

        adapter = MatchesAdapter {
            viewModel.saveMatch(it)
        }
        recycler.adapter = adapter

        viewModel.visibleMatches.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        return view
    }
}

