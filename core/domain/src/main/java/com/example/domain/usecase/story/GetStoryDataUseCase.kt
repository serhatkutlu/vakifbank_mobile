package com.example.domain.usecase.story

import com.example.domain.repository.abstraction.StoryRepository
import javax.inject.Inject

class GetStoryDataUseCase @Inject constructor(private val storyRepository: StoryRepository) {

    suspend operator  fun invoke()=storyRepository.getStoryData()
}