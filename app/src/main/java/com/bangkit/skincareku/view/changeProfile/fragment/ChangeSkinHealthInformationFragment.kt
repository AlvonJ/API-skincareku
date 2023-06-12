package com.bangkit.skincareku.view.biodata.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.FragmentSkinHealthInformationBinding
import com.bangkit.skincareku.networking.data.DataManager
import com.bangkit.skincareku.view.biodata.ChangeProfileProgressBarListener
import com.bangkit.skincareku.view.biodata.ChangeProfileViewModel

class ChangeSkinHealthInformationFragment : Fragment() {

    private var _binding: FragmentSkinHealthInformationBinding? = null
    private val binding get() = _binding!!
    private var changeProfileProgressBarListener: ChangeProfileProgressBarListener? = null
    private val skinProblems = mutableListOf<String>()

    private lateinit var changeProfileViewModel: ChangeProfileViewModel

    val dataManager: DataManager by lazy {
        DataManager(requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSkinHealthInformationBinding.inflate(inflater, container, false)

        changeProfileProgressBarListener?.updateProgressBar(50)
        changeProfileViewModel = ChangeProfileViewModel()
        changeProfileViewModel.getUserByEmail(dataManager.getEmail().toString())

        changeProfileViewModel.profile.observe(requireActivity(), {
            skinProblems.addAll(it.data?.fieldsProto?.skinProblem?.stringValue.toString().split(", ").toTypedArray())
            println(skinProblems)
            if(skinProblems.contains("Acne")) {
                binding.btnAcne.setTextColor(resources.getColor(R.color.blue))
                binding.btnAcne.setBackgroundResource(R.drawable.button_outline_selected)
            }
            if(skinProblems.contains("Dry Skin")) {
                binding.btnDrySkin.setTextColor(resources.getColor(R.color.blue))
                binding.btnDrySkin.setBackgroundResource(R.drawable.button_outline_selected)
            }
            if(skinProblems.contains("Oily Skin")) {
                binding.btnOilySkin.setTextColor(resources.getColor(R.color.blue))
                binding.btnOilySkin.setBackgroundResource(R.drawable.button_outline_selected)
            }
        })

        val fragmentManager = requireActivity().supportFragmentManager

        binding.btnNext.setOnClickListener {
            if(skinProblems.size > 0) {
                saveData()
                fragmentManager.beginTransaction()
                    .replace(R.id.fl_biodata, ChangeAllergyInformationFragment())
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right)
                    .addToBackStack(null)
                    .commit()
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

    private fun saveData() {
        var skinProblemString = skinProblems.joinToString(", ")
        dataManager.saveSkinProblem(skinProblemString)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        changeProfileProgressBarListener = context as? ChangeProfileProgressBarListener
        if(changeProfileProgressBarListener == null) {
            throw ClassCastException("$context must implement ProgressBarListener")
        }
    }
}