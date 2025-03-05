package com.example.marketknowledge.presentation.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.marketknowledge.databinding.FragmentMarketBinding
import com.example.marketknowledge.presentation.adapter.MarketKnowledgeAdapter
import com.example.marketknowledge.presentation.adapter.SwipeController
import com.example.marketknowledge.presentation.viewmodel.MarketKnowledgeViewModel
import com.example.ui.base.BaseFragment
import com.example.ui.extensions.extension.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : BaseFragment<FragmentMarketBinding>(FragmentMarketBinding::inflate) {

    private val viewmodel: MarketKnowledgeViewModel by viewModels()

    private val adapter: MarketKnowledgeAdapter by lazy {
        MarketKnowledgeAdapter()
    }
    private var swipeController:SwipeController?=null

    override fun initUi() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
         swipeController = SwipeController(requireContext(), binding.rvMarket)
        val itemTouchHelper = ItemTouchHelper(swipeController!!)
        itemTouchHelper.attachToRecyclerView(binding.rvMarket)
        binding.rvMarket.adapter = adapter
    }

    override fun initObservers() {
        launchAndRepeatWithViewLifecycle {
            viewmodel.state.collect {
                adapter.submitList(it.marketData.data)
            }
        }
    }

}