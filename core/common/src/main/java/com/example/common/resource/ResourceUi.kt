package com.example.common.resource

sealed class ResourceUi<T>(
    val data: T? = null,
) {
    class Success<T>(data: T? = null) : ResourceUi<T>(data)

    class Idle<T> : ResourceUi<T>()

    class Loading<T> : ResourceUi<T>()


}


