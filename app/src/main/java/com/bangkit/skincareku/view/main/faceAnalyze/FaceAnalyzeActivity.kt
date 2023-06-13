package com.bangkit.skincareku.view.main.faceAnalyze

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bangkit.skincareku.databinding.ActivityFaceAnalyzeBinding
import com.bangkit.skincareku.networking.response.ModelResponse
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaceAnalyzeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaceAnalyzeBinding
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var outputTextView: TextView
    private var GALLERY_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = binding.imageView
        button = binding.bntCaptureImage
        outputTextView = binding.outputTextView
        val buttonLoad = binding.btnLoadImage

        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                takePicturePreview.launch(null)
            } else {
                requestPermission.launch(android.Manifest.permission.CAMERA)
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
                            val label = maxEntry.key
                            outputTextView.text = label
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
}