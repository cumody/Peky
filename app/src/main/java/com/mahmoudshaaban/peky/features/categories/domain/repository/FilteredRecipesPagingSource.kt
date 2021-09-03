package com.mahmoudshaaban.peky.features.categories.domain.repository

import androidx.paging.PagingSource
import com.mahmoudshaaban.peky.core.constants.K
import com.mahmoudshaaban.peky.core.domain.model.Recipe
import com.mahmoudshaaban.peky.core.data.network.model.asDomainModel
import com.mahmoudshaaban.peky.core.data.network.services.RecipeService
import com.mahmoudshaaban.peky.core.error.HttpLimitExceededException
import retrofit2.HttpException


class FilteredRecipesPagingSource(
    private val service: RecipeService,
    private val options: Map<String, String>
) : PagingSource<Int, Recipe>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        try {
            val pageNumber = params.key ?: 1
            val pageSize = K.RECIPES_PER_PAGE
            val offset = (pageNumber - 1) * pageSize
            val response = service.searchRecipes(
                options = options,
                addRecipeInformation = true,
                number = pageSize,
                offset = offset
            )
            val nextPageNumber = if (response.totalResults - pageSize > offset && offset < 900) {
                pageNumber + 1

            } else {
                null
            }
            return LoadResult.Page(
                data = response.results.map { it.asDomainModel() },
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: HttpException) {
            if (e.code() == 402) {
                return LoadResult.Error(HttpLimitExceededException())
            }
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}