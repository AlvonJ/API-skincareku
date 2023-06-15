package com.bangkit.skincareku.view.main.dashboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.ItemProductBinding
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bangkit.skincareku.networking.response.Product
import com.bangkit.skincareku.view.main.buyProduct.DetailProductActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ProductRecommendationAdapter (private val productRecommendationList: ArrayList<GetAllProductItem>) :
    RecyclerView.Adapter<ProductRecommendationAdapter.ListViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(productRecommendationList[position])
        holder.itemView.setOnClickListener {
            val intentdetail = Intent(holder.itemView.context, DetailProductActivity::class.java)
            intentdetail.putExtra("key_product", productRecommendationList[holder.adapterPosition])
            holder.itemView.context.startActivity(intentdetail)
        }

    }

    override fun getItemCount(): Int = productRecommendationList.size

    class ListViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productItem: GetAllProductItem) {
            binding.tvProductTitle.text = productItem.data.productName
            binding.tvProductDescription.text = productItem.data.description
            binding.tvPrice.text = "$ " +  productItem.data.price
            binding.tvRating.text = productItem.data.rating.toString()

            Glide.with(itemView.context)
                .load(productItem.data.imageUrl)
                .apply(RequestOptions()
                    .placeholder(R.drawable.product)
                    .override(300, 300)
                    .centerCrop())
                .into(binding.ivProduct)
        }
    }

}


