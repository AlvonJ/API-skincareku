package com.bangkit.skincareku.view.main.dashboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skincareku.databinding.ItemArticleBinding
import com.bangkit.skincareku.networking.response.Article
import com.bangkit.skincareku.networking.response.ArticleResponse
import com.bangkit.skincareku.networking.response.ArticlesItem
import com.bangkit.skincareku.view.article.DetailArticleActivity
import com.bumptech.glide.Glide

class ArticleAdapter (private val articleList: ArrayList<ArticlesItem>) :
    RecyclerView.Adapter<ArticleAdapter.ListViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemArticleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(articleList[position])
        holder.binding.cvArticle.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailArticleActivity::class.java)
            intent.putExtra("url", articleList[position].link)
            startActivity(holder.itemView.context, intent, null)
        }

    }

    override fun getItemCount(): Int = articleList.size

    class ListViewHolder(var binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articleItem: ArticlesItem) {
            binding.tvArticleTitle.text = articleItem.title
            binding.tvArticleAuthor.text = articleItem.rights
            Glide.with(itemView.context)
                .load(articleItem.media)
                .into(binding.imageView)
        }
    }
}