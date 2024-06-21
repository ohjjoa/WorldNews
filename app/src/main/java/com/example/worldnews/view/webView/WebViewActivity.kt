package com.example.worldnews.view.webView

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.worldnews.R
import com.example.worldnews.databinding.ActivityMainBinding
import com.example.worldnews.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val url = intent.getStringExtra("url")

        binding.apply {
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true

            if (url != null) {
                webView.loadUrl(url)
            }

            imgClose.setOnClickListener {
                finish()
            }
        }
    }
}