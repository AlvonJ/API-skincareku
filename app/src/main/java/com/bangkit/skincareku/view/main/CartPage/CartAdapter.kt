package com.bangkit.skincareku.view.main.CartPage

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bangkit.skincareku.databinding.ItemCartBinding
import com.bangkit.skincareku.networking.database.ItemCart
import com.bangkit.skincareku.view.main.buyProduct.DetailProductActivity
import com.bumptech.glide.Glide

class CartAdapter(private val viewModel: CartViewModel) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var listProduk = emptyList<ItemCart>()
    fun updateList(newList: List<ItemCart>){
        val diffCall = DiffUtil.calculateDiff(ItemDiffCallback(listProduk, newList))
        this.listProduk = newList

        diffCall.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {

        holder.bind(listProduk[position])

    }

    override fun getItemCount(): Int = listProduk.size

    class ViewHolder(val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cart: ItemCart){
            with(binding){
                Glide.with(itemView.context)
                    .load(cart.imageUrl)
                    .into(ivcart)
                tvnameproduct.text= cart.productName
                priceproduct.text= cart.price.toString()
            }

        }
    }
}