package com.archive.mynews.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldnews.api.NewsError
import com.example.worldnews.api.NewsRepository
import com.example.worldnews.api.NewsResponse
import com.example.worldnews.api.ResultInterface
import com.example.worldnews.databinding.FragmentEverythingBinding
import com.example.worldnews.view.everything.EverythingInterface
import com.example.worldnews.view.webView.WebViewActivity


/**
 * A simple [Fragment] subclass.
 */
class EverythingFragment : Fragment(), EverythingInterface {

    private lateinit var _binding: FragmentEverythingBinding

    private val binding get() = _binding

    private lateinit var adapter : EverythingAdapter
    private var page = 1

    // 처음 키워드
    private var keyword = "korea"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEverythingBinding.inflate(layoutInflater, container, false)
        return binding. root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search()
        init()
    }

    private fun init() {
        binding.apply {
            adapter = EverythingAdapter(requireContext(), everythingInterface = this@EverythingFragment)
            recyclerEverything.adapter = adapter
            recyclerEverything.layoutManager = LinearLayoutManager(requireContext())

            newsSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        val imm: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                        page = 1
                        keyword = newsSearch.text.toString()
                        search()
                    }

                    else -> {
                        Toast.makeText(context, "검색실패", Toast.LENGTH_SHORT).show()
                        return@OnEditorActionListener false
                    }
                }
                true
            })

            recyclerEverything.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerEverything.canScrollVertically(1)) {
                        NewsRepository.getKeywordNews(keyword, page = page, callback = object : ResultInterface<NewsResponse> {
                            override fun onSuccess(response: NewsResponse) {
                                adapter.addArticleList(response.articles)
                                page += 1
                            }

                            override fun onFailure(error: NewsError) {

                            }
                        })
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                }
            })
        }
    }

    // 검색을 수행하는 메소드
    private fun search() {
        val recycler = binding.recyclerEverything
        NewsRepository.getKeywordNews(keyword, page = page, callback = object : ResultInterface<NewsResponse> {
            override fun onSuccess(response: NewsResponse) {
                Toast.makeText(context, "검색성공", Toast.LENGTH_SHORT).show()
                recycler.scrollToPosition(0)
                adapter.replaceArticleList(response.articles)
                page += 1
            }

            override fun onFailure(error: NewsError) {
                Toast.makeText(context, "검색실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(url: String) {
        val intent = Intent(requireContext(), WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }
}