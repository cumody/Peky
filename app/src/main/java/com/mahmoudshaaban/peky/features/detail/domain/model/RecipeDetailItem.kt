package com.mahmoudshaaban.peky.features.detail.domain.model

import com.mahmoudshaaban.peky.core.domain.model.Ingredient
import com.mahmoudshaaban.peky.core.domain.model.Instruction

sealed class RecipeDetailItem {
    abstract val id: Int


    data class Summary(val text: String, override val id: Int) : RecipeDetailItem()

    data class SectionTitle(val text: String, override val id: Int) : RecipeDetailItem()

    data class RecipeIngredient(val ingredient: Ingredient) : RecipeDetailItem() {
        override val id: Int
            get() = ingredient.id
    }

    data class RecipeDirection(val instruction: Instruction) : RecipeDetailItem() {
        override val id: Int
            get() = instruction.number + instruction.hashCode()

    }

}