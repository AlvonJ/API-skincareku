package com.bangkit.skincareku.view.main.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skincareku.databinding.FragmentDashboardBinding
import com.bangkit.skincareku.networking.data.DataManager
import com.bangkit.skincareku.networking.response.Article
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import com.bangkit.skincareku.view.profile.ProfileActivity

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

        val articleLayoutManager = LinearLayoutManager(requireActivity())
        articleLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvArticle.layoutManager = articleLayoutManager

        ApiConfig.init(requireActivity())
        dashboardViewModel = DashboardViewModel()
        setupViewModel()

        binding.ivProfile.setOnClickListener {
            val mIntent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(mIntent)
        }

    }

    private fun setupViewModel() {

        val acneIngredients = listOf("Salicylic Acid")
        // Sulfur

        val comedoIngredients = listOf("Retinol")
        // Clay", "Retinol", "Niacinamide", "Zinc", "Salicylic Acid", "Benzoyl Peroxide"

        val clearSkinIngredients = listOf("Niacinamide")

        val dataManager = DataManager(requireContext())
        dashboardViewModel.getUserByEmail(dataManager.getEmail().toString())

        dashboardViewModel.getArticle()

        dashboardViewModel.profile.observe(requireActivity(), { profile ->
            val skinProblemArray = profile.data?.fieldsProto?.skinProblem?.stringValue?.split(", ")?.toTypedArray()
            val skinProblemArrayList = ArrayList<String>(skinProblemArray?.toList())
            println("getProductRecommendationDashboard")
            println(skinProblemArrayList)
            println(acneIngredients)
            if (skinProblemArrayList.contains("Acne")) {
                dashboardViewModel.getProductRecommendation(acneIngredients)
            }else if(skinProblemArrayList.contains("Comedo")){
                dashboardViewModel.getProductRecommendation(comedoIngredients)
            }else if(skinProblemArrayList.contains("Clear Skin")){
                dashboardViewModel.getProductRecommendation(clearSkinIngredients)
            }
        })

        dashboardViewModel.productRecommendationList.observe(requireActivity(), { list ->
            setProductRecommendation(list)
        })

        dashboardViewModel.articleList.observe(requireActivity(), { list ->
            setArticle(list)
        })
    }

    private fun setProductRecommendation(list: List<GetAllProductItem>) {
        val listProduct = ArrayList<GetAllProductItem>()

        for(item in list) {
            listProduct.add(
                GetAllProductItem(
                    item.data,
                    item.id
                )
            )
        }

        val adapter = ProductRecommendationAdapter(listProduct)
        binding.rvProduct.adapter = adapter
    }

    private fun setArticle(list: List<Article>) {
        val listArticle = ArrayList<Article>()

        for(item in list) {
            listArticle.add(
                Article(
                    item.title,
                    item.duration,
                )
            )
        }

        val adapter = ArticleAdapter(listArticle)
        binding.rvArticle.adapter = adapter
    }
}