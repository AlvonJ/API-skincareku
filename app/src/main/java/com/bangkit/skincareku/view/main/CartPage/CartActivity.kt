package com.bangkit.skincareku.view.main.CartPage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skincareku.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity(){
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val cartViewModel: CartViewModel by viewModels{ factory }

        adapter = CartAdapter()

        binding.apply {
            rvCart.layoutManager = LinearLayoutManager(this@CartActivity)
            rvCart.setHasFixedSize(true)
            rvCart.adapter = adapter
        }

        cartViewModel.getCartItem().observe(this){ cartList ->
            adapter.updateList(cartList)
        }
    }
}