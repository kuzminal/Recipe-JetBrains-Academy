package recipes.controller;

import org.springframework.web.bind.annotation.*;
import recipes.domain.Recipe;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    Recipe resipes = new Recipe();

    @GetMapping
    public Recipe getAllRecipe() {
        return resipes;
    }

    @PostMapping
    public void saveRecipe(@RequestBody Recipe recipe) {
        resipes.setName(recipe.getName());
        resipes.setDescription(recipe.getDescription());
        resipes.setIngredients(recipe.getIngredients());
        resipes.setDirections(recipe.getDirections());
    }
}
