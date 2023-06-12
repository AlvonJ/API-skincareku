package com.bangkit.skincareku.view.profile

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.ActivityProfileBinding
import com.bangkit.skincareku.networking.data.DataManager
import com.bangkit.skincareku.view.biodata.ChangeProfileActivity
import com.bangkit.skincareku.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        profileViewModel = ProfileViewModel()


        setupViewModel()
        setup()

    }

    private fun setupViewModel() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        profileViewModel.isSuccessful.observe(this, {
            if (it == true) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else {
                Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
            }
        })

        profileViewModel.isLoading.observe(this, {
            if (it) {
                progressDialog = ProgressDialog(this)
                progressDialog.show()
                progressDialog.setContentView(R.layout.item_progress_dialog)
                progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }else {
                progressDialog = ProgressDialog(this)
                progressDialog.dismiss()
            }
        })

        profileViewModel.isConnectionError.observe(this, {
            if(it){
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.no_internet))
                    setMessage(getString(R.string.connection_error))
                    setPositiveButton(getString(R.string.retry)) { _, _ ->
                        setup()
                    }
                    create()
                    show()
                }
            }
        })

        profileViewModel.isGetProfileSuccessful.observe(this, {
            if (it == true) {
                binding.tvName.text = firebaseUser?.displayName
                binding.tvEmail.text = profileViewModel.profile.value?.data?.fieldsProto?.email?.stringValue
            }
        })
    }

    private fun setup () {
        val dataManager = DataManager(this)

        profileViewModel.getUserByEmail(dataManager.getEmail().toString())

        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.btnChangeProfile.setOnClickListener {
            val intent = Intent(this, ChangeProfileActivity::class.java)
            startActivity(intent) }
    }
}