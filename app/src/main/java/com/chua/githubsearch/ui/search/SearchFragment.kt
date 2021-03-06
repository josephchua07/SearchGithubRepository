package com.chua.githubsearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chua.githubsearch.databinding.FragmentSearchBinding
import com.chua.githubsearch.network.Status
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

            textField.setEndIconOnClickListener {
                searchViewModel.reset()
                searchViewModel.search(searchEditText.text.toString())
            }

            loadMore.setOnClickListener {
                searchViewModel.loadMore()
            }
        }

        searchViewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Loading -> {
                    showLoading(true)
                }
                is Status.Error -> {
                    showLoading(false)
                    searchAdapter.updateItems()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is Status.Success -> {
                    showLoading(false, loadMore = true)
                    searchAdapter.updateItems(it.data)
                }
            }

        }

    }

    private fun showLoading(loading: Boolean, loadMore: Boolean = false) {
        binding.searchProgressBar.isVisible = loading
        binding.textField.isEndIconVisible = !loading
        binding.loadMore.isVisible = loadMore
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}