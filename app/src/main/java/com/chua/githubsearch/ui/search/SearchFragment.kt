package com.chua.githubsearch.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chua.githubsearch.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: get string from edit text
        searchViewModel.search("budget")

        //TODO: display items in recyclerview
        searchViewModel.items.observe(viewLifecycleOwner) { items ->
            Log.d("search", items.size.toString())

            for (item in items) {
                Log.d("search", item.full_name)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}