package com.example.data.repository.story

import com.example.common.resource.Resource
import com.example.common.resource.transform
import com.example.data.mapper.toStoryUiData
import com.example.data.repository.BaseRepository
import com.example.datasource.storydatasource.abstraction.StoryDataSource
import com.example.domain.model.story.StoryUiData
import com.example.domain.repository.abstraction.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(private val storyDataSource: StoryDataSource) :StoryRepository,BaseRepository() {
    override suspend fun getStoryData(): Flow<Resource<List<StoryUiData>>> {
        return safeApiCall {
            storyDataSource.getStoryData()
        }.map { resource ->
            resource.transform {
                Resource.Success(it?.map { it.toStoryUiData() })
            }
        }
    }
}