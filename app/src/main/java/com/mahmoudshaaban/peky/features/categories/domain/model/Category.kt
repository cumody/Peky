package com.mahmoudshaaban.peky.features.categories.domain.model

data class Category(
    val name: String,
    val items: List<CategoryItem>
)