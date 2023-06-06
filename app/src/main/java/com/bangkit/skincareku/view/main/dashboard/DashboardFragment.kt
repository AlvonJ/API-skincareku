package com.bangkit.skincareku.view.main.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.FragmentDashboardBinding
import com.bangkit.skincareku.networking.response.Product
import com.bangkit.skincareku.networking.retrofit.ApiConfig

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvProduct.layoutManager = layoutManager

        ApiConfig.init(requireActivity())
        dashboardViewModel = DashboardViewModel()
        setupViewModel()

    }

    private fun setupViewModel() {
        dashboardViewModel.getProductRecommendation()

        dashboardViewModel.productRecommendationList.observe(requireActivity(), { list ->
            setProductRecommendation(list)
        })
    }

    private fun setProductRecommendation(list: List<Product>) {
        val listProduct = ArrayList<Product>()

        for(item in list) {
            listProduct.add(
                Product(
                    item.title,
                    item.description,
                    item.like
                )
            )
        }

        val adapter = ProductRecommendationAdapter(listProduct, dashboardViewModel)
        binding.rvProduct.adapter = adapter
    }
}