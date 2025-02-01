package com.example.dto.story.response

data class StoryDtoItem(
    val contentUrl: String,
    val iconUrl: String,
    val isVideo: Boolean,
    val referralLink: String,
    val title: String
)