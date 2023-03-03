package com.example.food2work
data class RecipeSearchResponse(
    val results: List<RecipeModel>?
)


class RecipeModel {
    var pk: Int
        get() = field
        set(value) {
            field=value
        }
    var title: String
        get() = field
        set(value) {
            field = value
        }

    var description: String
        get() = field
        set(value) {
            field = value
        }

    var featured_image: String
        get() = field
        set(value) {
            field = value
        }

    var ingredients: List<String>
        get() = field
        set(value) {
            field = value
        }

    constructor(id_recipe: Int,recipe_title: String, description_recipe: String, image_recipe: String, ingredients: List<String>) {
        this.pk = id_recipe
        this.title = recipe_title
        this.description = description_recipe
        this.featured_image = image_recipe
        this.ingredients = ingredients
    }
}
