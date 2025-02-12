package com.example.story.presentation.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.model.story.StoryUiData
import com.example.story.presentation.fragments.StoryItemFragment

class StoryAdapter(fragmentActivity: Fragment) : FragmentStateAdapter(fragmentActivity) {

    private val storyList = mutableListOf<StoryUiData>()

    fun submitList(stories: List<StoryUiData>) {
        storyList.clear()
        storyList.addAll(stories)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = storyList.size

    override fun createFragment(position: Int): Fragment {
        val story = storyList[position]
        return StoryItemFragment.newInstance(story)
    }
}
