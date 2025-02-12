package com.example.domain.model.story


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class StoryUiData(
    val contentUrl: String,
    val iconUrl: String,
    val isVideo: Boolean,
    val referralLink: String,
    val title: String
): Parcelable