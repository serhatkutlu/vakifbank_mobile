package com.example.datasource.storydatasource.implementation

import com.example.datasource.storydatasource.abstraction.StoryDataSource
import com.example.dto.story.response.StoryDtoItem
import com.example.service.StoryService
import retrofit2.Response
import javax.inject.Inject

class StoryDataSourceImpl @Inject constructor(private val storyService: StoryService):StoryDataSource {
    override suspend fun getStoryData(): Response<List<StoryDtoItem>> =storyService.getStoryData()

}