package com.simform.ssfurnicraftar.data.model

import com.google.gson.annotations.SerializedName

data class SketchfabResponse(
    @SerializedName("results")
    val results: List<SketchfabModel>
)

data class SketchfabModel(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("thumbnails")
    val thumbnails: SketchfabThumbnails?,
    @SerializedName("viewerUrl")
    val viewerUrl: String?,
    @SerializedName("downloadUrl")
    val downloadUrl: String?
)

data class SketchfabThumbnails(
    @SerializedName("images")
    val images: List<SketchfabImage>?
)

data class SketchfabImage(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int
)
