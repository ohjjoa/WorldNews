package com.archive.mynews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldnews.api.NewsError
import com.example.worldnews.api.NewsRepository
import com.example.worldnews.api.ResultInterface
import com.example.worldnews.api.SourceResponse
import com.example.worldnews.databinding.FragmentSourceBinding

/**
 * A simple [Fragment] subclass.
 */
class SourceFragment : Fragment() {

    private var _binding: FragmentSourceBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter : SourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.apply {
            adapter = SourceAdapter(requireContext())
            recyclerSource.adapter = adapter
            recyclerSource.layoutManager  = LinearLayoutManager(requireContext())
        }

        NewsRepository.getNewsProviders(object : ResultInterface<SourceResponse> {
            override fun onSuccess(response: SourceResponse) {
                adapter.addSourceList(response.sources)
            }

            override fun onFailure(error: NewsError) {

            }

        })
    }
}
