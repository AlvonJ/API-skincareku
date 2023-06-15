package com.bangkit.skincareku.view.main.buyProduct

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.ActivityDetailProdukBinding
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailProductActivity : AppCompatActivity(){
    private lateinit var btnAddCart: Button
    private lateinit var binding: ActivityDetailProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataProduct = intent.getParcelableExtra("key_product") as GetAllProductItem?

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

    }

}