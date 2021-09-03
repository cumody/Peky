package com.mahmoudshaaban.peky.core.data.network.model

import com.google.gson.annotations.SerializedName
import com.mahmoudshaaban.peky.core.data.network.model.NetworkRecipe

data class RecipeSearchResponse(
    @SerializedName("results")
    val results: List<NetworkRecipe>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("totalResults")
    val totalResults: Int
)