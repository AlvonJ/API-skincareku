package com.bangkit.skincareku.view.main.buyProduct

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.FragmentBuyProductBinding
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import com.bangkit.skincareku.view.main.CartPage.CartActivity
import com.bangkit.skincareku.view.main.dashboard.ProductRecommendationAdapter


class BuyProductFragment : Fragment() {

    private var _binding: FragmentBuyProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var buyProductViewModel: BuyProductViewModel
    private var proggresDialog : Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvproduct.layoutManager = layoutManager

        ApiConfig.init(requireActivity())
        buyProductViewModel = BuyProductViewModel()
        setupViewModel()
        buyProductViewModel.getProductAll()

        proggresDialog = Dialog(requireContext())
        proggresDialog?.setCancelable(false)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.buy_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                buyProductViewModel.searchProduct(newText.orEmpty())
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.it_cart ->{
                val intent = Intent(requireContext(), CartActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel(){
        buyProductViewModel.productList.observe(requireActivity(), { list ->
            getAllProduct(list)
        })

        buyProductViewModel.filteredProductList.observe(viewLifecycleOwner, { list ->
            getAllProduct(list)
        })

        buyProductViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                proggresDialog?.show()
                proggresDialog?.setContentView(R.layout.item_progress_dialog)
                proggresDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            } else {
                proggresDialog?.dismiss()
            }
        })

    }

    private fun getAllProduct(list: ArrayList<GetAllProductItem>){
        val listProduct = ArrayList<GetAllProductItem>()

        for (item in list){
            listProduct.add(
                GetAllProductItem(
                    item.data,
                    item.id
                )
            )
        }

        val adapter = ProductRecommendationAdapter(listProduct)
        binding.rvproduct.adapter = adapter
    }

}