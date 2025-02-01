package com.example.datasource.storydatasource.abstraction

import com.example.dto.story.response.StoryDtoItem
import retrofit2.Response

interface StoryDataSource {

    suspend fun getStoryData(): Response<List<StoryDtoItem>>
}