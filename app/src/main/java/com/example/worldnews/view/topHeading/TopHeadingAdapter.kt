package com.example.worldnews.view.topHeading

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worldnews.model.Article
import com.example.worldnews.R
import com.example.worldnews.databinding.ItemTopHeadingBinding
import java.text.SimpleDateFormat
import java.util.*


class TopHeadingAdapter(
    val context: Context,
    private val articleList: MutableList<Article> = mutableListOf(),
    private val topHeadingInterface: TopHeadingInterface? = null
) : RecyclerView.Adapter<TopHeadingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopHeadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(articleList[position], context)
    }

    fun addArticleList(articleList: List<Article>) {
        this.articleList.addAll(articleList)
        notifyDataSetChanged()
    }

    fun replaceArticleList(articleList: List<Article>) {
        this.articleList.clear()
        this.articleList.addAll(articleList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTopHeadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(article: Article, context: Context) {
            val timeFormat = SimpleDateFormat(
                context.getString(R.string.format_date),
                Locale.getDefault()
            )
            val time = timeFormat.format(article.publishedAt)

            binding.apply {
                textTopHeadingDate.text = time
                textTopHeadingTitle.text = article.title
                textTopHeadingContent.text = article.description
                textTopHeadingMedia.text = article.url

                Glide.with(context).load(article.urlToImage).into(imageTopHeading)

                layoutTopHeading.setOnClickListener {
                    topHeadingInterface?.onItemClick(article.url)
                }
            }
        }
    }
}

