package com.bangkit.skincareku.view.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skincareku.networking.response.Feed

class FeedViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _feedList = MutableLiveData<ArrayList<Feed>>()
    val feedList: LiveData<ArrayList<Feed>> = _feedList

    fun getFeedList () {
        _isLoading.value = true
        _isError.value = false
        val feedList = ArrayList<Feed>()
        feedList.add(
            Feed(
                "Name 1",
                "https://modelsaber.com/files/avatar/1575000962/image.jpg",
                "https://cdn.shopify.com/s/files/1/1607/6973/articles/Skincare_Routine-HDB_1400x.progressive.jpg?v=1614706558",
                "Caption 1",
                10,
                10,
                "Time 1"
            )
        )
        feedList.add(
            Feed(
                "Name 2",
                "https://modelsaber.com/files/avatar/1575000962/image.jpg",
                "https://cdn.shopify.com/s/files/1/1607/6973/articles/Skincare_Routine-HDB_1400x.progressive.jpg?v=1614706558",
                "Caption 2",
                20,
                20,
                "Time 2"
            )
        )
        feedList.add(
            Feed(
                "Name 3",
                "https://modelsaber.com/files/avatar/1575000962/image.jpg",
                "https://cdn.shopify.com/s/files/1/1607/6973/articles/Skincare_Routine-HDB_1400x.progressive.jpg?v=1614706558",
                "Caption 3",
                30,
                30,
                "Time 3"
            )
        )
            _feedList.value = feedList
        _isLoading.value = false
    }
}