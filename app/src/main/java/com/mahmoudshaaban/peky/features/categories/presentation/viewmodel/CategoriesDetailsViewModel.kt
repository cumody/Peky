package com.mahmoudshaaban.peky.features.categories.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mahmoudshaaban.peky.core.constants.K
import com.mahmoudshaaban.peky.core.data.network.services.RecipeService
import com.mahmoudshaaban.peky.core.domain.model.Recipe
import com.mahmoudshaaban.peky.features.categories.domain.model.CategoryItem
import com.mahmoudshaaban.peky.features.categories.domain.repository.FilteredRecipesPagingSource
import kotlinx.coroutines.flow.Flow

class CategoriesDetailsViewModel @ViewModelInject constructor(
    private val service: RecipeService
) : ViewModel() {

    var recipesFlow: Flow<PagingData<Recipe>>? = null

    fun requestRecipesForCategory(categoryItem: CategoryItem) {
        recipesFlow = Pager(PagingConfig(K.RECIPES_PER_PAGE)) {
            val options = mapOf(categoryItem.type to categoryItem.type)
            FilteredRecipesPagingSource(service, options)
        }.flow
            .cachedIn(viewModelScope)
    }
}