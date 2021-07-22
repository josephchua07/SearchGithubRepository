package com.chua.githubsearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chua.githubsearch.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()

    private val searchAdapter = SearchAdapter { url ->
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToWebFragment(url))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchRecyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = searchAdapter
            }

            searchButton.setOnClickListener {
                searchViewModel.search(searchEditText.text.toString())
            }
        }

        searchViewModel.items.observe(viewLifecycleOwner) { items ->
            searchAdapter.updateItems(items)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}