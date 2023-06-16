package com.bangkit.skincareku.view.main.CartPage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skincareku.databinding.ActivityCartBinding
import com.bangkit.skincareku.networking.database.ItemCart

class CartActivity : AppCompatActivity(){
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter

    private val cartViewModel by viewModels<CartViewModel>() {
        ViewModelFactory.getInstance(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val cartViewModel: CartViewModel by viewModels{ factory }

        adapter = CartAdapter(cartViewModel)

        binding.apply {
            rvCart.layoutManager = LinearLayoutManager(this@CartActivity)
            rvCart.adapter = adapter
        }

        cartViewModel.getCartItem().observe(this){ cartList ->
            adapter.updateList(cartList)
        }
    }
}