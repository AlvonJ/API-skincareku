package com.bangkit.skincareku.view.main.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skincareku.databinding.ItemFeedBinding
import com.bangkit.skincareku.networking.response.Feed
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FeedAdapter (private val feedList: ArrayList<Feed>) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(feedList[position])
    }

    override fun getItemCount(): Int = feedList.size

    class FeedViewHolder(var binding: ItemFeedBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(feedItem: Feed) {
            binding.tvName.text = feedItem.name
            binding.tvContent.text = feedItem.caption
            binding.tvTime.text = feedItem.time

            Glide.with(itemView.context)
                .load(feedItem.profile)
                .apply(RequestOptions().override(60, 60))
                .into(binding.ivProfile)

            Glide.with(itemView.context)
                .load(feedItem.image)
                .apply(RequestOptions().override(300, 300))
                .into(binding.ivContent)
        }
    }
}