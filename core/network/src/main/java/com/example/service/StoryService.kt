package com.example.service

import com.example.dto.story.response.StoryDtoItem
import com.example.endpoint.StoryEndpoint
import retrofit2.Response
import retrofit2.http.GET

interface StoryService {

    @GET(StoryEndpoint.path)
    suspend fun getStoryData(): Response<List<StoryDtoItem>>
}