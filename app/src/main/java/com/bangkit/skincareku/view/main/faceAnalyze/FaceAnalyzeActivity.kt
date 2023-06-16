package com.bangkit.skincareku.view.main.faceAnalyze

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.ActivityFaceAnalyzeBinding
import com.bangkit.skincareku.networking.database.ItemCart
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bangkit.skincareku.networking.response.ModelResponse
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import com.bangkit.skincareku.view.main.CartPage.CartActivity
import com.bangkit.skincareku.view.main.CartPage.CartViewModel
import com.bangkit.skincareku.view.main.CartPage.ViewModelFactory
import com.bangkit.skincareku.view.main.MainActivity
import com.bangkit.skincareku.view.main.buyProduct.DetailProductActivity
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class FaceAnalyzeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaceAnalyzeBinding
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var outputTextView: TextView
    private lateinit var label: String
    private lateinit var cleanser: ItemCart
    private lateinit var toner: ItemCart
    private lateinit var moisturizer: ItemCart
    private lateinit var serum: ItemCart
    private var GALLERY_REQUEST_CODE = 123
    private var progressDialog: Dialog? = null

    private lateinit var faceAnalyzeViewModel: FaceAnalyzeViewModel
    private val cartViewModel by viewModels<CartViewModel>() {
        ViewModelFactory.getInstance(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        ApiConfig.init(this)
        faceAnalyzeViewModel = FaceAnalyzeViewModel()

        progressDialog = Dialog(this)
        progressDialog?.setCancelable(false)

        imageView = binding.imageView
        button = binding.bntCaptureImage
        outputTextView = binding.tvDescription
        val buttonLoad = binding.btnLoadImage

        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                takePicturePreview.launch(null)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
        buttonLoad.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                val mimeType = arrayOf("image/jpeg", "image/png", "image/jpg")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                onResult.launch(intent)
            } else {
                requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

    }

    private fun setupViewModel(label: String) {
        val acneIngredients = listOf("niacinamide", "alicylic acid", "benzoyl peroxide", "salicylic acid")
        val comedoIngredients = listOf("bentonite", "glycolic acid", "lactic acid")
        val clearSkinIngredients = listOf("hyaluronic acid", "vitamin c", "ceramide")
        var hasCleanser = 0
        var hasToner = 0
        var hasMoisturizer = 0
        var hasSerum = 0
        var cleanserData : GetAllProductItem? = null
        var tonerData : GetAllProductItem? = null
        var moisturizerData : GetAllProductItem? = null
        var serumData : GetAllProductItem? = null

        faceAnalyzeViewModel.getCleanserRecommendation()
        faceAnalyzeViewModel.cleanser.observe(this, { list ->
            for (cleanser in list) {
                if(label == "acne") {
                    val containIngredients = acneIngredients.any{cleanser.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindCleanser(cleanser.id, cleanser.data.productName, cleanser.data.description, cleanser.data.price, cleanser.data.rating.toString(), cleanser.data.imageUrl)
                        cleanserData = cleanser
                        hasCleanser = 1
                        break
                    }
                } else if (label == "comedo") {
                    val containIngredients = comedoIngredients.any{cleanser.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindCleanser(cleanser.id, cleanser.data.productName, cleanser.data.description, cleanser.data.price, cleanser.data.rating.toString(), cleanser.data.imageUrl)
                        cleanserData = cleanser
                        hasCleanser = 1
                        break
                    }
                } else if (label == "clean") {
                    val containIngredients = clearSkinIngredients.any{cleanser.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindCleanser(cleanser.id, cleanser.data.productName, cleanser.data.description, cleanser.data.price, cleanser.data.rating.toString(), cleanser.data.imageUrl)
                        cleanserData = cleanser
                        hasCleanser = 1
                        break
                    }
                }
            }
            if(hasCleanser == 0){
                binding.cvCleanser.visibility = View.GONE
                binding.tvCleanser.visibility = View.GONE
            }else{
                binding.cvCleanser.visibility = View.VISIBLE
                binding.tvCleanser.visibility = View.VISIBLE

                binding.cvCleanser
            }
        })

        faceAnalyzeViewModel.getMoisturizerRecommendation()
        faceAnalyzeViewModel.moisturizer.observe(this, { list ->
            for (moisturizer in list) {
                if(label == "acne") {
                    val containIngredients = acneIngredients.any{moisturizer.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindMoisturizer(moisturizer.id, moisturizer.data.productName, moisturizer.data.description, moisturizer.data.price, moisturizer.data.rating.toString(), moisturizer.data.imageUrl)
                        moisturizerData = moisturizer
                        hasMoisturizer = 1
                        break
                    }
                } else if (label == "comedo") {
                    val containIngredients = comedoIngredients.any{moisturizer.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindMoisturizer(moisturizer.id, moisturizer.data.productName, moisturizer.data.description, moisturizer.data.price, moisturizer.data.rating.toString(), moisturizer.data.imageUrl)
                        moisturizerData = moisturizer
                        hasMoisturizer = 1
                        break
                    }
                } else if (label == "clean") {
                    val containIngredients = clearSkinIngredients.any{moisturizer.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindMoisturizer(moisturizer.id, moisturizer.data.productName, moisturizer.data.description, moisturizer.data.price, moisturizer.data.rating.toString(), moisturizer.data.imageUrl)
                        moisturizerData = moisturizer
                        hasMoisturizer = 1
                        break
                    }
                }
            }
            if(hasMoisturizer == 0){
                binding.cvMoisturizer.visibility = View.GONE
                binding.cvCleanser.visibility = View.GONE
            }else{
                binding.cvMoisturizer.visibility = View.VISIBLE
                binding.cvCleanser.visibility = View.VISIBLE
            }
        })

        faceAnalyzeViewModel.getTonerRecommendation()
        faceAnalyzeViewModel.toner.observe(this, { list ->
            for (toner in list) {
                if(label == "acne") {
                    val containIngredients = acneIngredients.any{toner.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindToner(toner.id, toner.data.productName, toner.data.description, toner.data.price, toner.data.rating.toString(), toner.data.imageUrl)
                        tonerData = toner
                        hasToner = 1
                        break
                    }
                } else if (label == "comedo") {
                    val containIngredients = comedoIngredients.any{toner.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindToner(toner.id, toner.data.productName, toner.data.description, toner.data.price, toner.data.rating.toString(), toner.data.imageUrl)
                        tonerData = toner
                        hasToner = 1
                        break
                    }
                } else if (label == "clean") {
                    val containIngredients = clearSkinIngredients.any{toner.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindToner(toner.id, toner.data.productName, toner.data.description, toner.data.price, toner.data.rating.toString(), toner.data.imageUrl)
                        tonerData = toner
                        hasToner = 1
                        break
                    }
                }
            }

            if(hasToner == 0){
                binding.cvToner.visibility = View.GONE
                binding.tvToner.visibility = View.GONE
            }else{
                binding.cvToner.visibility = View.VISIBLE
                binding.tvToner.visibility = View.VISIBLE
            }
        })

        faceAnalyzeViewModel.getSerumRecommendation()
        faceAnalyzeViewModel.serum.observe(this, { list ->
            for (serum in list) {
                if(label == "acne") {
                    val containIngredients = acneIngredients.any{serum.data.ingredients.contains(it)}
                    println(containIngredients)
                    if (containIngredients) {
                        bindSerum(serum.id, serum.data.productName, serum.data.description, serum.data.price, serum.data.rating.toString(), serum.data.imageUrl)
                        serumData = serum
                        hasSerum = 1
                        break
                    }
                } else if (label == "comedo") {
                    val containIngredients = comedoIngredients.any{serum.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindSerum(serum.id, serum.data.productName, serum.data.description, serum.data.price, serum.data.rating.toString(), serum.data.imageUrl)
                        serumData = serum
                        hasSerum = 1
                        break
                    }
                } else if (label == "clean") {
                    val containIngredients = clearSkinIngredients.any{serum.data.ingredients.contains(it)}
                    if (containIngredients) {
                        bindSerum(serum.id, serum.data.productName, serum.data.description, serum.data.price, serum.data.rating.toString(), serum.data.imageUrl)
                        serumData = serum
                        hasSerum = 1
                        break
                    }
                }
            }
            if(hasSerum == 0){
                binding.cvSerum.visibility = View.GONE
                binding.tvSerum.visibility = View.GONE
            }else{
                binding.cvSerum.visibility = View.VISIBLE
                binding.tvSerum.visibility = View.VISIBLE
            }
        })

        binding.cvCleanser.setOnClickListener {
            val intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra("key_product", cleanserData)
            startActivity(intent)
        }

        binding.cvMoisturizer.setOnClickListener {
            val intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra("key_product", moisturizerData)
            startActivity(intent)
        }

        binding.cvSerum.setOnClickListener {
            val intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra("key_product", serumData)
            startActivity(intent)
        }

        binding.cvToner.setOnClickListener {
            val intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra("key_product", tonerData)
            startActivity(intent)
        }

        faceAnalyzeViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                progressDialog?.show()
                progressDialog?.setContentView(R.layout.item_progress_dialog)
                progressDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            } else {
                progressDialog?.dismiss()
            }
        })

        binding.btnSubmit.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage(R.string.buying_recommended_product)
                .setPositiveButton("Yes") { dialog, which ->
                    println(binding.cbCleanser.isChecked)
                    if(binding.cbCleanser.isChecked) {
                        cartViewModel.addDelItem(cleanser, false)
                    }
                    if(binding.cbMousturizer.isChecked) {
                        cartViewModel.addDelItem(moisturizer, false)
                    }
                    if(binding.cbSerum.isChecked) {
                        cartViewModel.addDelItem(serum, false)
                    }
                    if(binding.cbToner.isChecked) {
                        cartViewModel.addDelItem(toner, false)
                    }
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, which ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .show()
        }
    }

    private fun bindCleanser (id: String, title: String, description: String, price: String, rating: String, imageUrl: String) {
        cleanser = ItemCart(id, title, imageUrl, price, true)
        binding.tvCleanserTitle.text = title
        binding.tvCleanserDescription.text = description
        binding.tvCleanserPrice.text = price
        binding.tvCleanserRating.text = rating
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivCleanserImage)
    }

    private fun bindMoisturizer (id: String, title: String, description: String, price: String, rating: String, imageUrl: String) {
        moisturizer = ItemCart(id, title, imageUrl, price, true)
        binding.tvMoisturizerTitle.text = title
        binding.tvMoisturizerDescription.text = description
        binding.tvMoisturizerPrice.text = price
        binding.tvMoisturizerRating.text = rating
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivMoisturizerImage)
    }

    private fun bindToner (id: String, title: String, description: String, price: String, rating: String, imageUrl: String) {
        toner = ItemCart(id, title, imageUrl, price, true)
        binding.tvTonerTitle.text = title
        binding.tvTonerDescription.text = description
        binding.tvTonerPrice.text = price
        binding.tvTonerRating.text = rating
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivTonerImage)
    }

    private fun bindSerum (id: String, title: String, description: String, price: String, rating: String, imageUrl: String) {
        serum = ItemCart(id, title, imageUrl, price, true)
        binding.tvSerumTitle.text = title
        binding.tvSerumDescription.text = description
        binding.tvSerumPrice.text = price
        binding.tvSerumRating.text = rating
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivSerumImage)
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePicturePreview.launch(null)
            } else {
                Toast.makeText(this, "Permission Denied! Try Again.", Toast.LENGTH_SHORT).show()
            }
        }

    private val takePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                outputGenerator(bitmap)
            }
        }

    private val onResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i("TAG", "This is the result: ${result.data} ${result.resultCode}")
            onResultReceived(GALLERY_REQUEST_CODE, result)
        }

    private fun onResultReceived(requestCode: Int, result: ActivityResult?) {
        when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (result?.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        Log.i("TAG", "onResultReceived: $uri")
                        val bitmap =
                            BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        imageView.setImageBitmap(bitmap)
                        outputGenerator(bitmap)
                    }
                } else {
                    Log.e("TAG", "onActivityResult: error in selecting image")
                }
            }
        }
    }

    private fun outputGenerator(bitmap: Bitmap) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val requestBody = outputStream.toByteArray().toRequestBody("image/jpeg".toMediaType())

        val baseUrl = "http://34.101.209.86:5000/"
        val apiService = ApiConfig.getApiService(baseUrl)
        val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

        val call = apiService.predictImage(imagePart)
        call.enqueue(object : Callback<ModelResponse> {
            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful) {
                    val modelResponse = response.body()
                    if (modelResponse != null) {
                        val prediction = modelResponse.prediction
                        val maxEntry = prediction.maxByOrNull { it.value }
                        if (maxEntry != null) {
                            label = maxEntry.key
                            outputTextView.text = getString(R.string.skin_analysis, label)
                            println("Label : " + label)
                            binding.clRecommendation.visibility = View.VISIBLE
                            setupViewModel(label)
                        }
                    } else {
                        Toast.makeText(
                            this@FaceAnalyzeActivity,
                            "Empty response from server",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@FaceAnalyzeActivity,
                        "Response not success.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                t.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@FaceAnalyzeActivity,
                        "Request onfail",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }
}