package com.bangkit.skincareku.view.main.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.FragmentDashboardBinding
import com.bangkit.skincareku.networking.data.DailyChecklistDataManager
import com.bangkit.skincareku.networking.data.DataManager
import com.bangkit.skincareku.networking.response.ArticlesItem
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import com.bangkit.skincareku.view.profile.ProfileActivity
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel
    private var progressDialog : Dialog? = null
    private lateinit var dailyChecklistDataManager: DailyChecklistDataManager

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

        dailyChecklistDataManager = DailyChecklistDataManager(requireActivity())

        ApiConfig.init(requireActivity())
        dashboardViewModel = DashboardViewModel()

        progressDialog = Dialog(requireContext())
        progressDialog?.setCancelable(false)

        setupViewModel()
        setupDailyChecklist()
        setup()

        binding.ivProfile.setOnClickListener {
            val mIntent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(mIntent)
        }

    }

    override fun onResume() {
        super.onResume()
        setupDailyChecklist()
    }

    private fun setup() {
        val (hour, minute) = getCurrentTimeUsingCalendar()
        val currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()

        if(dailyChecklistDataManager.getDate() != currentDate){
            dailyChecklistDataManager.clear()
            dailyChecklistDataManager.saveDate(currentDate)
        }

        binding.cvGetup.setOnClickListener {
            if(dailyChecklistDataManager.getGetup() == null) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.getup_title)
                    .setMessage(R.string.getup_description)
                    .setPositiveButton(R.string.ok) { dialog, which ->
                        dailyChecklistDataManager.saveGetup(hour.toString() + ":" + minute.toString())
                        setupDailyChecklist()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.cvCleanserMorning.setOnClickListener {
            if(dailyChecklistDataManager.getCleanserMorning() == false) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.cleanser_title)
                    .setMessage(R.string.cleanser_description)
                    .setPositiveButton(R.string.ok) { dialog, which ->
                        dailyChecklistDataManager.saveCleanserMorning(true)
                        setupDailyChecklist()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.cvTonerMorning.setOnClickListener {
            if(dailyChecklistDataManager.getTonerMorning() == false) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.toner_title)
                    .setMessage(R.string.toner_description)
                    .setPositiveButton(R.string.ok) { dialog, which ->
                        dailyChecklistDataManager.saveTonerMorning(true)
                        setupDailyChecklist()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.cvMoisturizerMorning.setOnClickListener {
            if(dailyChecklistDataManager.getMoisturizerMorning() == false) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.moisturizer_title)
                    .setMessage(R.string.moisturizer_description)
                    .setPositiveButton(R.string.ok) { dialog, which ->
                        dailyChecklistDataManager.saveMoisturizerMorning(true)
                        setupDailyChecklist()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.cvCleanserNight.setOnClickListener {
            if(dailyChecklistDataManager.getCleanserNight() == false) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.cleanser_title)
                    .setMessage(R.string.cleanser_description)
                    .setPositiveButton(R.string.ok) { dialog, which ->
                        dailyChecklistDataManager.saveCleanserNight(true)
                        setupDailyChecklist()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.cvTonerNight.setOnClickListener {
            if(dailyChecklistDataManager.getTonerNight() == false) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.toner_title)
                    .setMessage(R.string.toner_description)
                    .setPositiveButton(R.string.ok) { dialog, which ->
                        dailyChecklistDataManager.saveTonerNight(true)
                        setupDailyChecklist()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.cvSerumNight.setOnClickListener {
            if(dailyChecklistDataManager.getSerumNight() == false) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.serum_title)
                    .setMessage(R.string.serum_description)
                    .setPositiveButton(R.string.ok) { dialog, which ->
                        dailyChecklistDataManager.saveSerumNight(true)
                        setupDailyChecklist()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.cvSleep.setOnClickListener {
            if(dailyChecklistDataManager.getSleep() == null) {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.sleep_title)
                    .setMessage(R.string.sleep_description)
                    .setPositiveButton(R.string.ok) { dialog, which ->
                        dailyChecklistDataManager.saveSleep(hour.toString() + ":" + minute.toString())
                        setupDailyChecklist()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }
    private fun setupDailyChecklist () {
        val currentTime = Calendar.getInstance().time

        if(currentTime.hours <= 18) {
            binding.clMorning.visibility = View.VISIBLE
            binding.clNight.visibility = View.GONE

            if(dailyChecklistDataManager.getGetup() != null){
                binding.tvGetupContent.text = dailyChecklistDataManager.getGetup()
                binding.cvGetup.setCardBackgroundColor(resources.getColor(R.color.light_pink))
                binding.tvGetup.setTextColor(resources.getColor(R.color.black))
            }else{
                binding.tvGetupContent.text = "--:--"
                binding.cvGetup.setCardBackgroundColor(resources.getColor(R.color.grey))
                binding.tvGetup.setTextColor(resources.getColor(R.color.white))
            }

            if(dailyChecklistDataManager.getCleanserMorning()){
                binding.ivCleanserMorning.setImageResource(R.drawable.baseline_check_circle_outline_green)
                binding.cvCleanserMorning.setCardBackgroundColor(resources.getColor(R.color.light_pink))
                binding.tvCleanserMorning.setTextColor(resources.getColor(R.color.black))
            }else{
                binding.ivCleanserMorning.setImageResource(R.drawable.baseline_check_circle_outline_24_white)
                binding.cvCleanserMorning.setCardBackgroundColor(resources.getColor(R.color.grey))
                binding.tvCleanserMorning.setTextColor(resources.getColor(R.color.white))
            }

            if(dailyChecklistDataManager.getTonerMorning()){
                binding.ivTonerMorning.setImageResource(R.drawable.baseline_check_circle_outline_green)
                binding.cvTonerMorning.setCardBackgroundColor(resources.getColor(R.color.light_pink))
                binding.tvTonerMorning.setTextColor(resources.getColor(R.color.black))
            }else{
                binding.ivTonerMorning.setImageResource(R.drawable.baseline_check_circle_outline_24_white)
                binding.cvTonerMorning.setCardBackgroundColor(resources.getColor(R.color.grey))
                binding.tvTonerMorning.setTextColor(resources.getColor(R.color.white))
            }

            if(dailyChecklistDataManager.getMoisturizerMorning()){
                binding.ivMoisturizerMorning.setImageResource(R.drawable.baseline_check_circle_outline_green)
                binding.cvMoisturizerMorning.setCardBackgroundColor(resources.getColor(R.color.light_pink))
                binding.tvMoisturizerMorning.setTextColor(resources.getColor(R.color.black))
            }else{
                binding.ivMoisturizerMorning.setImageResource(R.drawable.baseline_check_circle_outline_24_white)
                binding.cvMoisturizerMorning.setCardBackgroundColor(resources.getColor(R.color.grey))
                binding.tvMoisturizerMorning.setTextColor(resources.getColor(R.color.white))
            }

        }else {
            binding.clMorning.visibility = View.GONE
            binding.clNight.visibility = View.VISIBLE

            if(dailyChecklistDataManager.getCleanserNight()){
                binding.ivCleanserNight.setImageResource(R.drawable.baseline_check_circle_outline_green)
                binding.cvCleanserNight.setCardBackgroundColor(resources.getColor(R.color.light_pink))
                binding.tvCleanserNight.setTextColor(resources.getColor(R.color.black))
            }else{
                binding.ivCleanserNight.setImageResource(R.drawable.baseline_check_circle_outline_24_white)
                binding.cvCleanserNight.setCardBackgroundColor(resources.getColor(R.color.grey))
                binding.tvCleanserNight.setTextColor(resources.getColor(R.color.white))
            }

            if(dailyChecklistDataManager.getTonerNight()){
                binding.ivTonerNight.setImageResource(R.drawable.baseline_check_circle_outline_green)
                binding.cvTonerNight.setCardBackgroundColor(resources.getColor(R.color.light_pink))
                binding.tvTonerNight.setTextColor(resources.getColor(R.color.black))
            }else{
                binding.ivTonerNight.setImageResource(R.drawable.baseline_check_circle_outline_24_white)
                binding.cvTonerNight.setCardBackgroundColor(resources.getColor(R.color.grey))
                binding.tvTonerNight.setTextColor(resources.getColor(R.color.white))
            }

            if(dailyChecklistDataManager.getSerumNight()){
                binding.ivSerumNight.setImageResource(R.drawable.baseline_check_circle_outline_green)
                binding.cvSerumNight.setCardBackgroundColor(resources.getColor(R.color.light_pink))
                binding.tvSerumNight.setTextColor(resources.getColor(R.color.black))
            }else{
                binding.ivSerumNight.setImageResource(R.drawable.baseline_check_circle_outline_24_white)
                binding.cvSerumNight.setCardBackgroundColor(resources.getColor(R.color.grey))
                binding.tvSerumNight.setTextColor(resources.getColor(R.color.white))
            }

            if(dailyChecklistDataManager.getSleep() != null){
                binding.tvSleepContent.text = dailyChecklistDataManager.getGetup()
                binding.cvSleep.setCardBackgroundColor(resources.getColor(R.color.light_pink))
                binding.tvGetup.setTextColor(resources.getColor(R.color.black))
            }else{
                binding.tvSleepContent.text = "--:--"
                binding.cvSleep.setCardBackgroundColor(resources.getColor(R.color.grey))
                binding.tvSleep.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    private fun setupViewModel() {

        val acneIngredients = listOf("niacinamide", "alicylic acid", "benzoyl peroxide", "salicylic acid")
        // Sulfur

        val comedoIngredients = listOf("bentonite", "glycolic acid", "lactic acid")
        // Clay", "Retinol", "Niacinamide", "Zinc", "Salicylic Acid", "Benzoyl Peroxide"

        val clearSkinIngredients = listOf("hyaluronic acid", "vitamin c", "ceramide")

        val dataManager = DataManager(requireContext())
        dashboardViewModel.getUserByEmail(dataManager.getEmail().toString())

        dashboardViewModel.profile.observe(requireActivity(), { profile ->
            val skinProblemArray = profile.data?.fieldsProto?.skinProblem?.stringValue?.split(", ")?.toTypedArray()
            val skinProblemArrayList = ArrayList<String>(skinProblemArray?.toList())
            println("getProductRecommendationDashboard")
            println(skinProblemArrayList)
            println(acneIngredients)
            if (skinProblemArrayList.contains("Acne")) {
                dashboardViewModel.getProductRecommendation(acneIngredients)
//                dashboardViewModel.getArticle("acne skincare")
            }else if(skinProblemArrayList.contains("Comedo")){
                dashboardViewModel.getProductRecommendation(comedoIngredients)
//                dashboardViewModel.getArticle("comedo skincare")
            }else if(skinProblemArrayList.contains("Clear Skin")){
                dashboardViewModel.getProductRecommendation(clearSkinIngredients)
//                dashboardViewModel.getArticle("how to take care of facial skin")
            }
        })

        dashboardViewModel.productRecommendationList.observe(requireActivity(), { list ->
            setProductRecommendation(list)
        })

        dashboardViewModel.articleList.observe(requireActivity(), { list ->
            setArticle(list)
        })

        dashboardViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                progressDialog?.show()
                progressDialog?.setContentView(R.layout.item_progress_dialog)
                progressDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            } else {
                progressDialog?.dismiss()
            }
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

    private fun setArticle(list: List<ArticlesItem>) {
        val listArticle = ArrayList<ArticlesItem>()

        for(item in list) {
            listArticle.add(
                ArticlesItem(
                    item.summary,
                    item.country,
                    item.author,
                    item.link,
                    item.language,
                    item.media,
                    item.title,
                    item.score,
                    item.cleanUrl,
                    item.publishedDatePrecision,
                    item.rights,
                    item.isOpinion,
                    item.rank,
                    item.topic,
                    item.twitterAccount,
                    item.id,
                    item.excerpt,
                    item.publishedDate,
                    item.authors
                )
            )
        }

        val adapter = ArticleAdapter(listArticle)
        binding.rvArticle.adapter = adapter
    }

    fun getCurrentTimeUsingCalendar(): Pair<Int, Int> {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return hour to minute
    }
}