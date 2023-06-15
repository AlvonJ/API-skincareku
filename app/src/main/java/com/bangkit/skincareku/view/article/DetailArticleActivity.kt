package com.bangkit.skincareku.view.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.bangkit.skincareku.databinding.ActivityArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        webView = binding.wbArticle
        webView.webViewClient = WebViewClient()

        webView.settings.javaScriptEnabled = true

        val url = intent.getStringExtra("url")
        webView.loadUrl(url.toString())

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}