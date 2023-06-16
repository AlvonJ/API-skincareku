package com.bangkit.skincareku.view.main.buyProduct

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.ActivityDetailProdukBinding
import com.bangkit.skincareku.networking.database.ItemCart
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bangkit.skincareku.view.main.CartPage.CartViewModel
import com.bangkit.skincareku.view.main.CartPage.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailProductActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailProdukBinding
    private val cartViewModel by viewModels<CartViewModel>() {
        ViewModelFactory.getInstance(
            application
        )
    }

    companion object{
        const val PRODUCT_KEY = "key_product"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataProduct = intent.getParcelableExtra("key_product") as GetAllProductItem?
        println("data product : $dataProduct")
        binding.apply {
            productName.text = dataProduct?.data?.productName
            descDetail.text = dataProduct?.data?.description
            ingredients.text = dataProduct?.data?.ingredients?.joinToString(",")
            goodrate.text = dataProduct?.data?.goodReviews.toString()
            badrate.text = dataProduct?.data?.badReviews.toString()
            price.text = "$" + dataProduct?.data?.price

            Glide.with(this@DetailProductActivity)
                .load(dataProduct?.data?.imageUrl)
                .apply(RequestOptions()
                    .placeholder(R.drawable.product)
                    .override(300,300)
                    .centerCrop())
                .into(ivproductdetail)
        }

        cartViewModel.getCartItem().observe(this){ cartList ->
            val isoncart = cartList.any{it.id == dataProduct?.id}

            setCartIcon(isoncart)

            binding.fbCart?.setOnClickListener{
                val isCarted = dataProduct?.data?.productName.let { productname ->
                    ItemCart(
                        id = dataProduct?.id.toString(),
                        productName = dataProduct?.data?.productName.toString(),
                        imageUrl = dataProduct?.data?.imageUrl.toString(),
                        price = "$" + dataProduct?.data?.price.toString(), false)
                }

                try {
                    if (isCarted != null) cartViewModel.addDelItem(isCarted, cartList.any{ it.id == dataProduct?.id})
                } catch (e : Exception){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT ).show()
                }

                if (isoncart){
                    Toast.makeText(this,"Product remove from cart", Toast.LENGTH_SHORT).show()
                    setCartIcon(isoncart)
                }else{
                    Toast.makeText(this,"Product add to cart", Toast.LENGTH_SHORT).show()
                    setCartIcon(isoncart)
                }
            }
        }

    }

    private fun setCartIcon(isoncart: Boolean){
        binding.fbCart.apply {
            if (isoncart){
                setImageDrawable(ContextCompat.getDrawable(this@DetailProductActivity,
                R.drawable.del_cart))
            }else{
                setImageDrawable(ContextCompat.getDrawable(this@DetailProductActivity,
                R.drawable.add_cart))
            }
        }
    }
}