package com.example.story.presentation.fragments



import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2

import com.example.story.databinding.FragmentStoryBinding
import com.example.story.presentation.adapter.StoryAdapter
import com.example.story.presentation.viewmodel.StoryViewModel
import com.example.story.vp2transformer.CubePageTransformer
import com.example.ui.base.BaseFragment
import com.example.ui.extensions.extension.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>(FragmentStoryBinding::inflate) {
    private lateinit var storyAdapter: StoryAdapter
    private val args: StoryFragmentArgs by navArgs()


    private val viewmodel: StoryViewModel by viewModels()


    override fun initUi() {
        initViewPager()
    }



    private fun initViewPager() {
        storyAdapter = StoryAdapter(this).apply {
            submitList(args.storyData.toList())

        }

        with(binding.includedVp) {
            vpStory.setAdapter(storyAdapter)
            vpStory.setCurrentItem(args.ordinal, false)
            vpStory.setPageTransformer(CubePageTransformer())
        }

    }




    override fun initObservers() {
        launchAndRepeatWithViewLifecycle {
            viewmodel.effect.collect {
                when (it) {
                    is StoryViewModel.Companion.StoryEffect.NextPage -> {
                        nextPage()
                    }

                    is StoryViewModel.Companion.StoryEffect.PreviousPage -> {
                        previousPage()
                    }
                    is StoryViewModel.Companion.StoryEffect.Close -> {
                        findNavController().popBackStack()
                    }
                    else -> {}
                }
            }

        }
    }
    private fun nextPage() {
        val currentItem = binding.includedVp.vpStory.currentItem
        val totalItems = storyAdapter.itemCount
        if (currentItem < totalItems - 1) {
            binding.includedVp.vpStory.setCurrentItem(currentItem + 1, true)
        } else {
            findNavController().popBackStack()
        }
    }

    private fun previousPage() {
        if (binding.includedVp.vpStory.currentItem != 0) {
            binding.includedVp.vpStory.setCurrentItem(
                binding.includedVp.vpStory.currentItem - 1,
                true
            )
        }else findNavController().popBackStack()

    }


}