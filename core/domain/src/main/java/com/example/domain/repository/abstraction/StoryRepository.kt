package com.example.domain.repository.abstraction

import com.example.common.resource.Resource
import com.example.domain.model.story.StoryUiData
import kotlinx.coroutines.flow.Flow

interface StoryRepository{

    suspend fun getStoryData(): Flow<Resource<List<StoryUiData>>>
}