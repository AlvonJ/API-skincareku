package com.bangkit.skincareku.view.biodata.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.FragmentSkinHealthInformationBinding
import com.bangkit.skincareku.view.biodata.ProgressBarListener
import com.bangkit.skincareku.view.main.MainActivity

class SkinHealthInformationFragment : Fragment() {

    private var _binding: FragmentSkinHealthInformationBinding? = null
    private val binding get() = _binding!!
    private var progressBarListener: ProgressBarListener? = null
    private val skinProblems = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSkinHealthInformationBinding.inflate(inflater, container, false)

        progressBarListener?.updateProgressBar(60)

        binding.btnSubmit.setOnClickListener {
            if(skinProblems.size > 0) {
                val sharedPref = requireActivity().getSharedPreferences("SKIN_PROBLEM", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("SKIN_PROBLEM", skinProblems.toString())
                editor.apply()

                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else {
                binding.tvSelectProblem.visibility = View.VISIBLE
            }

        }

        setupSkinProblem()

        return binding.root
    }

    private fun setupSkinProblem () {
        val acne = binding.btnAcne
        acne.setOnClickListener{
            if(skinProblems.contains("Acne")) {
                skinProblems.remove("Acne")
                acne.setTextColor(resources.getColor(R.color.grey))
                acne.setBackgroundResource(R.drawable.button_outline)
            } else {
                skinProblems.add("Acne")
                acne.setTextColor(resources.getColor(R.color.blue))
                acne.setBackgroundResource(R.drawable.button_outline_selected)
            }
        }

        val drySkin = binding.btnDrySkin
        drySkin.setOnClickListener{
            if(skinProblems.contains("Dry Skin")) {
                skinProblems.remove("Dry Skin")
                drySkin.setTextColor(resources.getColor(R.color.grey))
                drySkin.setBackgroundResource(R.drawable.button_outline)
            } else {
                skinProblems.add("Dry Skin")
                drySkin.setTextColor(resources.getColor(R.color.blue))
                drySkin.setBackgroundResource(R.drawable.button_outline_selected)
            }
        }

        val oilySkin = binding.btnOilySkin
        oilySkin.setOnClickListener{
            if(skinProblems.contains("Oily Skin")) {
                skinProblems.remove("Oily Skin")
                oilySkin.setTextColor(resources.getColor(R.color.grey))
                oilySkin.setBackgroundResource(R.drawable.button_outline)
            } else {
                skinProblems.add("Oily Skin")
                oilySkin.setTextColor(resources.getColor(R.color.blue))
                oilySkin.setBackgroundResource(R.drawable.button_outline_selected)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        progressBarListener = context as? ProgressBarListener
        if(progressBarListener == null) {
            throw ClassCastException("$context must implement ProgressBarListener")
        }
    }
}