package com.archive.mynews.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worldnews.model.Article
import com.example.worldnews.R
import com.example.worldnews.databinding.ItemEverythingBinding
import com.example.worldnews.view.everything.EverythingInterface
import com.example.worldnews.view.topHeading.TopHeadingInterface
import java.text.SimpleDateFormat
import java.util.Locale

class EverythingAdapter (
    val context: Context,
    private var articleList: MutableList<Article> = mutableListOf(),
    private val everythingInterface: EverythingInterface? = null
) : RecyclerView.Adapter<EverythingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEverythingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    // 리스트 갱신 메소드
    fun addArticleList(articleList: List<Article>) {
        this.articleList.addAll(articleList)
        notifyDataSetChanged()
    }

    fun replaceArticleList(articleList: List<Article>) {
        this.articleList = articleList as MutableList<Article>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(articleList[position], context)
    }

    inner class ViewHolder(private val binding: ItemEverythingBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bindView(article: Article, context: Context) {
            val timeFormat = SimpleDateFormat(
                context.getString(R.string.format_date),
                Locale.getDefault()
            )
            val time = timeFormat.format(article.publishedAt)

            binding.apply {
                textEverythingDate.text = time
                textEverythingTitle.text = article.title
                textEverythingContent.text = article.description
                textEverythingMedia.text = article.url

                Glide.with(context).load(article.urlToImage).into(imageEverything)

                layoutEverything.setOnClickListener {
                    everythingInterface?.onItemClick(article.url)
                }
            }
        }
    }
}