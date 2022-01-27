package com.b21cap0398.acnescan.ui.specificdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.b21cap0398.acnescan.R
import com.b21cap0398.acnescan.data.source.local.entity.FeedbackForm
import com.b21cap0398.acnescan.data.source.local.entity.MedicineInformation
import com.b21cap0398.acnescan.databinding.ActivitySpecificDetailBinding
import com.b21cap0398.acnescan.viewmodel.ViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import java.text.DecimalFormat

class SpecificDetailActivity : AppCompatActivity() {

    companion object {
        const val ACNE_NAME = "acneName"
        const val ACNE_POSSIBILITY = "acnePossibility"
    }

    // Binding
    private lateinit var binding: ActivitySpecificDetailBinding

    // Adapter
    private lateinit var acneImagesAdapter: AcneImagesAdapter
    private lateinit var recommendedMedicineAdapter: RecomendedMedicineAdapter

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var acneName: String
    private lateinit var acnePossibility: String

    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog

    private lateinit var viewModel: SpecificDetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecificDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading()

        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[SpecificDetailViewModel::class.java]

        val intent = intent
        acneName = intent.getStringExtra(ACNE_NAME).toString()
        val possibilityNum = intent.getDoubleExtra(ACNE_POSSIBILITY, 0.0)
        acnePossibility = DecimalFormat("##.#").format(possibilityNum)

        binding.tvAcneName.text = acneName[0].toUpperCase() + acneName.substring(1)
        binding.tvAcneNumberPossibility.text =
            getString(R.string.your_acne_is_similar_to_this_type_of_acne_about) + " " + acnePossibility + "%"

        viewModel.getAcneInformationById(acneName).observe(this, {
            binding.apply {
                tvAcneDescription.text = it.description
                tvCauseOfAcne.text = it.causes
                tvTipsToDeal.text = it.tips
                setAcneImagesAdapter(it.listImagePaths!!)

                val listProduct = arrayListOf<MedicineInformation>()

                for (i in 0 until (it.product_images!!.count() - 1)) {
                    val medicineInfo = MedicineInformation(
                        image_path = it.product_images[i],
                        name = it.product_names?.get(i) as String,
                        price = it.product_prices?.get(i) as String
                    )
                    listProduct.add(medicineInfo)
                }

                setRecommendedMedicines(listProduct)
            }

            hideLoading()
        })

        setFeedbackButtonOnClickListener()
        setBackButtonOnClickListener()
    }

    private fun setFeedbackButtonOnClickListener() {
        binding.civFeedbackButton.setOnClickListener {
            createNewFeedbackDialog()
        }
    }

    private fun setRecommendedMedicines(list: List<MedicineInformation>) {
        recommendedMedicineAdapter = RecomendedMedicineAdapter()
        recommendedMedicineAdapter.setList(list)
        binding.rvRecomendedMedicine.apply {
            layoutManager = LinearLayoutManager(
                this@SpecificDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            adapter = recommendedMedicineAdapter
        }
    }

    private fun setAcneImagesAdapter(list: List<String>) {
        acneImagesAdapter = AcneImagesAdapter()
        acneImagesAdapter.setList(list)
        binding.rvAcneImages.apply {
            layoutManager = LinearLayoutManager(
                this@SpecificDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            adapter = acneImagesAdapter

            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(binding.rvAcneImages)
        }
    }

    private fun setBackButtonOnClickListener() {
        binding.civBackButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showLoading() {
        val loadingScreen = binding.incLoading.root
        loadingScreen.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        val loadingScreen = binding.incLoading.root
        loadingScreen.visibility = View.GONE
    }

    @SuppressLint("InflateParams")
    private fun createNewFeedbackDialog() {
        dialogBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.feedback_popup, null)

        dialogBuilder.setView(view)
        dialog = dialogBuilder.create()
        dialog.show()

        view.findViewById<CardView>(R.id.civ_send_feedback).setOnClickListener {
            dialog.dismiss()
            val score_question_1 = view.findViewById<RatingBar>(R.id.rb_question_one).rating.toString()
            val score_question_2 = view.findViewById<RatingBar>(R.id.rb_question_two).rating.toString()
            val feedback = view.findViewById<TextInputLayout>(R.id.tf_suggestions).editText?.text.toString()

            val feedbackForm = FeedbackForm(score_question_1, score_question_2, feedback)

            viewModel.setFeedback(feedbackForm)

            createFeedbackSentDialog()
        }
    }

    private fun createFeedbackSentDialog() {
        dialogBuilder = AlertDialog.Builder(this)
        val sentView = layoutInflater.inflate(R.layout.feeback_sent_popup, null)

        dialogBuilder.setView(sentView)
        dialog = dialogBuilder.create()
        dialog.show()
    }
}