package com.example.food2work

class RecipeModel {
    var recipe_title: String
        get() = field
        set(value) {
            field = value
        }

    var description_recipe: String
        get() = field
        set(value) {
            field = value
        }

    var image_recipe: String
        get() = field
        set(value) {
            field = value
        }

    var nb_ingredient_recipe: Int
        get() = field
        set(value) {
            field = value
        }

    constructor(recipe_title: String, description_recipe: String, image_recipe: String, nb_ingredient_recipe: Int) {
        this.recipe_title = recipe_title
        this.description_recipe = description_recipe
        this.image_recipe = image_recipe
        this.nb_ingredient_recipe = nb_ingredient_recipe
    }
}
