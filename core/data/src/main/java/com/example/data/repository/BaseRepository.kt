package com.example.data.repository

import com.example.common.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

open class BaseRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    protected fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<com.example.common.resource.Resource<T>> {
        return flow {
            try {
            emit(com.example.common.resource.Resource.Loading())

            val response = withContext(dispatcher) {
                apiCall()
            }

            if (response.isSuccessful) {
                response.body().let {
                    emit(com.example.common.resource.Resource.Success(it))
                }
            }else{
                emit(com.example.common.resource.Resource.Error(text = com.example.common.resource.UiText.DynamicString(response.message())))

            }

        }catch (e: Exception) {
                emit(com.example.common.resource.Resource.Error(text = com.example.common.resource.UiText.DynamicString(e.message)))
            }

        }
    }
}