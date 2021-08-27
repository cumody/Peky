package com.mahmoudshaaban.peky.features.categories.presentation.dispatchers

import android.view.View
import com.mahmoudshaaban.peky.features.categories.domain.model.CategoryItem

interface CategoryEventDispatcher {
    fun onCategoryPressed(category: CategoryItem, view: View)
}