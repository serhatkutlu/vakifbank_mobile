package com.example.data.mapper

import com.example.domain.model.story.StoryUiData
import com.example.dto.story.response.StoryDtoItem

fun StoryDtoItem.toStoryUiData(): StoryUiData {
    return StoryUiData(
        contentUrl = contentUrl,
        iconUrl = iconUrl,
        isVideo = isVideo,
        referralLink = referralLink,
        title = title
    )
}