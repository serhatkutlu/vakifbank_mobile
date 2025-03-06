package com.example.story.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.domain.model.story.StoryUiData
import com.example.story.databinding.FragentStoryItemBinding
import com.example.story.presentation.customview.storyview.StoryViewListener
import com.example.story.presentation.viewmodel.StoryEvent
import com.example.story.presentation.viewmodel.StoryViewModel
import com.example.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class StoryItemFragment:BaseFragment<FragentStoryItemBinding>(FragentStoryItemBinding::inflate),
    StoryViewListener {


    private val viewmodel: StoryViewModel by viewModels({requireParentFragment()})

    private var args:StoryUiData?=null
    private var isPaused=false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args=arguments?.getParcelable(STORY_DATA_KEY)
        args?.let {
           binding.storyView.initUi(it)

        }

        binding.storyView.listener=this
    }


    override fun initUi() {

        initListener()
    }



    override fun initObservers() {
    }

    private fun initListener() {

    }

    override fun onResume() {
        super.onResume()
        binding.root.start()

    }


    override fun onPause() {
        super.onPause()
        binding.root.pause()
        isPaused=true
    }


    override fun onDestroyView() {
        binding.root.release()
        super.onDestroyView()
    }




    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)

    }
    companion object {
        private const val STORY_DATA_KEY = "storyData"

        fun newInstance(storyData:StoryUiData): StoryItemFragment {
            val fragment = StoryItemFragment()
            val args = Bundle()

            args.putParcelable(STORY_DATA_KEY, storyData)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onSwipeUp() {
        openLink(args?.referralLink?:"")
    }

    override fun onSwipeDown() {
        viewmodel.setEvent(StoryEvent.OnSwipeDown)
    }

    override fun onClickLeft() {
        viewmodel.setEvent(StoryEvent.PreviousClicked)    }

    override fun onClickRight() {
        viewmodel.setEvent(StoryEvent.NextClicked)
    }
}