package com.bangkit.skincareku.view.main.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.FragmentFeedBinding
import com.bangkit.skincareku.networking.response.Feed
import com.bangkit.skincareku.networking.retrofit.ApiConfig

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var feedViewModel: FeedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFeed.layoutManager = layoutManager

        ApiConfig.init(requireActivity())
        feedViewModel = FeedViewModel()
        setupViewModel()
    }

    private fun setupViewModel () {
        feedViewModel.getFeedList()

        feedViewModel.feedList.observe(requireActivity(), { list ->
            setFeed(list)
        })
    }

    private fun setFeed(list: List<Feed>) {
        val listFeed = ArrayList<Feed>()

        for(item in list) {
            val feed = Feed(
                item.name,
                item.profile,
                item.image,
                item.caption,
                item.likes,
                item.comments,
                item.time
            )
            listFeed.add(feed)
        }

        val feedAdapter = FeedAdapter(listFeed)
        binding.rvFeed.adapter = feedAdapter
    }

}