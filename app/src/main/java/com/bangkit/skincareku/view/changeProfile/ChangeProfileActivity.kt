package com.bangkit.skincareku.view.biodata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.ActivityBiodataBinding
import com.bangkit.skincareku.view.biodata.fragment.ChangeBasicInformationFragment
import com.bangkit.skincareku.view.biodata.fragment.ChangeSkinHealthInformationFragment


class ChangeProfileActivity : AppCompatActivity(), ChangeProfileProgressBarListener {
    private lateinit var binding: ActivityBiodataBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val ChangeBasicInformationFragment = ChangeBasicInformationFragment()
        val ChangeSkinHealthInformationFragment = ChangeSkinHealthInformationFragment()

        setCurrentFragment(ChangeBasicInformationFragment)

        progressBar = binding.pbBiodata
    }

    override fun updateProgressBar(value: Int) {
        progressBar.progress = value
    }

    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_biodata,fragment)
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
            commit()
        }
}