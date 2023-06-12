package com.bangkit.skincareku.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skincareku.networking.response.Email
import com.bangkit.skincareku.networking.response.UserDataResponse
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isConnectionError = MutableLiveData<Boolean>()
    val isConnectionError: LiveData<Boolean> = _isConnectionError
    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful
    private val _isGetProfileSuccessful = MutableLiveData<Boolean>()
    val isGetProfileSuccessful: LiveData<Boolean> = _isGetProfileSuccessful

    private val _profile = MutableLiveData<UserDataResponse>()
    val profile: LiveData<UserDataResponse> = _profile

    fun logout() {
        _isConnectionError.value = false
        _isLoading.value = true
        auth.signOut()
        println("logout")
        _isSuccessful.value = true
        _isLoading.value = false
    }

    fun getUserByEmail(email: String) {
        _isLoading.value = true
        _isConnectionError.value = false
        _isGetProfileSuccessful.value = false

        val client = ApiConfig.getApiService().getUserByEmail(email)
        client.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(
                call: Call<UserDataResponse>,
                response: Response<UserDataResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _profile.value = response.body()
                    _isGetProfileSuccessful.value = true
                } else {
                    _isGetProfileSuccessful.value = false
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                _isConnectionError.value = true
                _isLoading.value = false
            }
        })
        _isLoading.value = false
    }
}