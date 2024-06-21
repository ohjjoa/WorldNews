package com.example.worldnews.view.topHeading

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldnews.api.NewsError
import com.example.worldnews.api.NewsRepository
import com.example.worldnews.api.NewsResponse
import com.example.worldnews.api.ResultInterface
import com.example.worldnews.databinding.FragmentTopHeadingBinding
import com.example.worldnews.view.webView.WebViewActivity

class TopHeadingFragment : Fragment(), TopHeadingInterface {

    private var _binding: FragmentTopHeadingBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter : TopHeadingAdapter

    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopHeadingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.apply {
            adapter = TopHeadingAdapter(requireContext(), topHeadingInterface = this@TopHeadingFragment)
            recyclerTopHeading.adapter = adapter
            recyclerTopHeading.layoutManager = LinearLayoutManager(requireContext())

            NewsRepository.getTopHeadlines(page = page, callback = object : ResultInterface<NewsResponse> {
                override fun onSuccess(response: NewsResponse) {
                    adapter.addArticleList(response.articles)
                    page += 1
                }

                override fun onFailure(error: NewsError) {

                }
            })

            recyclerTopHeading.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerTopHeading.canScrollVertically(1)) {

                        NewsRepository.getTopHeadlines(page = page, callback = object : ResultInterface<NewsResponse> {
                            override fun onSuccess(response: NewsResponse) {
                                adapter.replaceArticleList(response.articles)
                                page = 1
                            }

                            override fun onFailure(error: NewsError) {

                            }
                        })
                    }
                }
            })
        }
    }

    fun refresh() {
        NewsRepository.getTopHeadlines(page = page, callback = object : ResultInterface<NewsResponse> {
            override fun onSuccess(response: NewsResponse) {
                adapter.replaceArticleList(response.articles)
                page = 1
            }

            override fun onFailure(error: NewsError) {

            }
        })
    }

    override fun onItemClick(url: String) {
        val intent = Intent(requireContext(), WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }
}