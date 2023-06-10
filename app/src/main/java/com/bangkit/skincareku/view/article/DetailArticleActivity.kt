package com.bangkit.skincareku.view.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bangkit.skincareku.databinding.ActivityArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var articleImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}