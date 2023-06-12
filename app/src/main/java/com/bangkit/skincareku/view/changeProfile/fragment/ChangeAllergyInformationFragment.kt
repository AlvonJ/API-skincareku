package com.bangkit.skincareku.view.biodata.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.bangkit.skincareku.R
import com.bangkit.skincareku.databinding.FragmentAllergyInformationBinding
import com.bangkit.skincareku.networking.data.DataManager
import com.bangkit.skincareku.view.biodata.ChangeProfileViewModel
import com.bangkit.skincareku.view.biodata.ChangeProfileProgressBarListener
import com.bangkit.skincareku.view.main.MainActivity

class ChangeAllergyInformationFragment : Fragment() {

    private var _binding: FragmentAllergyInformationBinding? = null
    private val binding get() = _binding!!
    private var changeProfileProgressBarListener: ChangeProfileProgressBarListener? = null
    private val allergies = mutableListOf<String>()
    private lateinit var changeProfileViewModel: ChangeProfileViewModel
    private lateinit var progressDialog: ProgressDialog

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
        _binding = FragmentAllergyInformationBinding.inflate(inflater, container, false)

        changeProfileViewModel = ChangeProfileViewModel()
        setupViewModel()
        changeProfileViewModel.getUserByEmail(dataManager.getEmail().toString())
        changeProfileViewModel.profile.observe(requireActivity(), {
            allergies.addAll(it.data?.fieldsProto?.allergy?.stringValue.toString().split(", ") as MutableList<String>)
            println(allergies)
            if(allergies.contains("Acids")) {
                binding.btnAcids.setTextColor(resources.getColor(R.color.blue))
                binding.btnAcids.setBackgroundResource(R.drawable.button_outline_selected)
            }
            if(allergies.contains("Essential Oils")) {
                binding.btnEssentialOils.setTextColor(resources.getColor(R.color.blue))
                binding.btnEssentialOils.setBackgroundResource(R.drawable.button_outline_selected)
            }
            if(allergies.contains("Sulfates")) {
                binding.btnSulfates.setTextColor(resources.getColor(R.color.blue))
                binding.btnSulfates.setBackgroundResource(R.drawable.button_outline_selected)
            }
        })

        changeProfileProgressBarListener?.updateProgressBar(80)



        binding.btnSubmit.setOnClickListener{
            if(allergies.size > 0) {
                saveData()
                val email = dataManager.getEmail().toString()
                val gender = dataManager.getGender().toString()
                val skinProblem = dataManager.getSkinProblem().toString()
                val allergy = dataManager.getAllergy().toString()
                val birthdate = dataManager.getBirthdate().toString()

                changeProfileViewModel.updateUser(email, gender, skinProblem, allergy, birthdate)
            }else {
                binding.tvSelectProblem.visibility = View.VISIBLE
            }
        }

        setupSkinProblem()

        return binding.root
    }

    private fun setupViewModel() {

        changeProfileViewModel.isSuccessful.observe(requireActivity(), {
            println("isSuccessful: $it")
            if(it){
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        })
        changeProfileViewModel.isConnectionError.observe(requireActivity(), {
            if(it){
                AlertDialog.Builder(requireContext()).apply {
                    setTitle(getString(R.string.no_internet))
                    setMessage(getString(R.string.connection_error))
                    setPositiveButton(getString(R.string.retry)) { _, _ ->
                        val email = dataManager.getEmail().toString()
                        val gender = dataManager.getGender().toString()
                        val skinProblem = dataManager.getSkinProblem().toString()
                        val allergy = dataManager.getAllergy().toString()
                        val birthdate = dataManager.getBirthdate().toString()

                        changeProfileViewModel.updateUser(email, gender, skinProblem, allergy, birthdate)
                    }
                    create()
                    show()
                }
            }
        })

        changeProfileViewModel.isLoading.observe(requireActivity(), {
            if (it) {
                progressDialog = ProgressDialog(requireContext())
                progressDialog.show()
                progressDialog.setContentView(R.layout.item_progress_dialog)
                progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }else {
                progressDialog.dismiss()
            }
        })
    }

    private fun setupSkinProblem () {
        val acids = binding.btnAcids
        acids.setOnClickListener{
            if(allergies.contains("Acids")) {
                allergies.remove("Acids")
                acids.setTextColor(resources.getColor(R.color.grey))
                acids.setBackgroundResource(R.drawable.button_outline)
            } else {
                allergies.add("Acids")
                acids.setTextColor(resources.getColor(R.color.blue))
                acids.setBackgroundResource(R.drawable.button_outline_selected)
            }
        }

        val essentialOils = binding.btnEssentialOils
        essentialOils.setOnClickListener{
            if(allergies.contains("Essential Oils")) {
                allergies.remove("Essential Oils")
                essentialOils.setTextColor(resources.getColor(R.color.grey))
                essentialOils.setBackgroundResource(R.drawable.button_outline)
            } else {
                allergies.add("Essential Oils")
                essentialOils.setTextColor(resources.getColor(R.color.blue))
                essentialOils.setBackgroundResource(R.drawable.button_outline_selected)
            }
        }

        val sulfates = binding.btnSulfates
        sulfates.setOnClickListener{
            if(allergies.contains("Sulfates")) {
                allergies.remove("Sulfates")
                sulfates.setTextColor(resources.getColor(R.color.grey))
                sulfates.setBackgroundResource(R.drawable.button_outline)
            } else {
                allergies.add("Sulfates")
                sulfates.setTextColor(resources.getColor(R.color.blue))
                sulfates.setBackgroundResource(R.drawable.button_outline_selected)
            }
        }
    }

    private fun saveData() {
        var allergyString = allergies.joinToString(", ")
        dataManager.saveAllergy(allergyString)
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