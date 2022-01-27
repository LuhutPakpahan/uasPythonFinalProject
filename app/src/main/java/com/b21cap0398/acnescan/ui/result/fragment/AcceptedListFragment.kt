package com.b21cap0398.acnescan.ui.result.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.b21cap0398.acnescan.R
import com.b21cap0398.acnescan.data.source.local.entity.AcneScanResult
import com.b21cap0398.acnescan.databinding.FragmentAcceptedListBinding
import com.b21cap0398.acnescan.ui.detail.DetailActivity
import com.b21cap0398.acnescan.ui.result.ResultViewModel
import com.b21cap0398.acnescan.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class AcceptedListFragment : Fragment() {

    private lateinit var binding: FragmentAcceptedListBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accepted_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAcceptedListBinding.bind(view)

        hideEmptyListWarning()
        binding.progressBar.visibility = View.VISIBLE

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[ResultViewModel::class.java]

        viewModel.getAllAcceptedAcneScanResult(auth.currentUser?.email!!)
            .observe(viewLifecycleOwner, { list ->
                val adapter = AcceptedListAdapter()
                adapter.setList(list.sortedBy { it.date })
                binding.rvAcceptedList.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter.setOnItemClickCallback(object :
                        AcceptedListAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: AcneScanResult) {
                            val intent = Intent(requireContext(), DetailActivity::class.java)
                            intent.putExtra(DetailActivity.RESULT_ID, data.result_id)
                            intent.putExtra(DetailActivity.ACNE_IMAGE_PATH, data.image_path)
                            startActivity(intent)
                        }
                    })
                    this.adapter = adapter
                }

                if (adapter.itemCount <= 0) {
                    showEmptyListWarning()
                }
                binding.progressBar.visibility = View.GONE
            })
    }

    private fun showEmptyListWarning() {
        val emptyListWarning = binding.warningEmptyList.root as View
        emptyListWarning.visibility = View.VISIBLE
    }

    private fun hideEmptyListWarning() {
        val emptyListWarning = binding.warningEmptyList.root as View
        emptyListWarning.visibility = View.GONE
    }
}