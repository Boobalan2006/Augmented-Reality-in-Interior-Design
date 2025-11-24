package com.simform.ssfurnicraftar.data.remote.api

import com.simform.ssfurnicraftar.data.model.SketchfabResponse
import com.simform.ssfurnicraftar.data.remote.apiresult.ApiResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SketchfabApiService {
    
    @GET("v3/search")
    suspend fun searchModels(
        @Header("Authorization") token: String,
        @Query("type") type: String = "models",
        @Query("q") query: String = "curtains",
        @Query("downloadable") downloadable: Boolean = true,
        @Query("sort_by") sortBy: String = "-likeCount",
        @Query("count") count: Int = 20
    ): ApiResult<SketchfabResponse>
    
    @GET("v3/search")
    suspend fun searchCurtains(
        @Header("Authorization") token: String,
        @Query("type") type: String = "models",
        @Query("q") query: String = "curtains drapes window treatment",
        @Query("downloadable") downloadable: Boolean = true,
        @Query("sort_by") sortBy: String = "-likeCount",
        @Query("count") count: Int = 50
    ): ApiResult<SketchfabResponse>
}
