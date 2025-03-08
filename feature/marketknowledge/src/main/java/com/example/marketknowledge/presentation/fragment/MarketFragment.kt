package com.example.marketknowledge.presentation.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.common.resource.ResourceUi
import com.example.marketknowledge.R
import com.example.marketknowledge.databinding.FragmentMarketBinding
import com.example.marketknowledge.presentation.adapter.MarketKnowledgeAdapter
import com.example.marketknowledge.presentation.adapter.SwipeController
import com.example.marketknowledge.presentation.viewmodel.MarketEffect
import com.example.marketknowledge.presentation.viewmodel.MarketEvent
import com.example.marketknowledge.presentation.viewmodel.MarketKnowledgeViewModel
import com.example.ui.base.BaseFragment
import com.example.ui.extensions.extension.gone
import com.example.ui.extensions.extension.launchAndRepeatWithViewLifecycle
import com.example.ui.extensions.extension.visible
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
        binding.toolbarMarket.ivRefresh.setOnClickListener{
            viewmodel.setEvent(MarketEvent.ClickRefresh)
        }
        binding.toolbarMarket.ivBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
         swipeController = SwipeController(requireContext(), binding.rvMarket)
        val itemTouchHelper = ItemTouchHelper(swipeController!!)
        itemTouchHelper.attachToRecyclerView(binding.rvMarket)
        binding.rvMarket.adapter = adapter
    }

    override fun initObservers() {
        collectState()
        collectEffect()

    }

    private fun collectEffect() {
        launchAndRepeatWithViewLifecycle {
            viewmodel.effect.collect{
                when(it){
                    is MarketEffect.showError->{
                        showCustomDialog(it.message?.asString(requireContext()).toString())
                        binding.progressCircular.gone()
                    }
                }
            }
        }
    }

    private fun collectState() {
        launchAndRepeatWithViewLifecycle {
            viewmodel.state.collect {
                when(it.marketData){
                    is ResourceUi.Success->{
                        adapter.submitList(it.marketData.data)
                        binding.tvLastUpdate.text=getString(R.string.market_last_update_header,it.lastUpdateDate)
                        binding.tvLastUpdate.visible()
                        binding.progressCircular.gone()



                    }
                    is ResourceUi.Loading->{
                        binding.progressCircular.visible()
                    }

                    is ResourceUi.Idle ->{}
                }


            }
        }
    }

}