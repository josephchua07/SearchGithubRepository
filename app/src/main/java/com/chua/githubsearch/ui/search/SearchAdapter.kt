package com.chua.githubsearch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chua.githubsearch.databinding.ItemBinding
import com.chua.githubsearch.model.Item

class SearchAdapter(
    private val navigate: (String) -> Unit
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var items: MutableList<Item> = mutableListOf()

    fun updateItems(items: List<Item> = emptyList()) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemBinding,
        val navigate: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                fullName.text = item.full_name
                description.text = item.description
                stargazersCount.text = "Stars: ${item.stargazers_count}"
                updatedAt.text = "Updated: ${item.updated_at}"
                root.setOnClickListener {
                    navigate(URL + item.full_name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, navigate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    companion object {
        const val URL = "https://github.com/"
    }

}