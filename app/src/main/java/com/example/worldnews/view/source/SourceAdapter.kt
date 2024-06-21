package com.archive.mynews.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldnews.databinding.ItemSourceBinding
import com.example.worldnews.model.Source

class SourceAdapter(
    val context: Context,
    private val sourceList: MutableList<Source> = mutableListOf()
) : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(sourceList[position])
    }

    fun addSourceList(sourceList: List<Source>) {
        this.sourceList.addAll(sourceList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(source: Source) {
            binding.apply {
                textSourceMedia.text = source.name
                textSourceContent.text = source.description
                textSourceCountry.text = source.country
                textSourceLanguage.text = source.language
            }
        }
    }
}