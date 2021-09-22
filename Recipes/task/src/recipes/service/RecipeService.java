package recipes.service;

import org.springframework.stereotype.Service;
import recipes.domain.Recipe;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            recipeRepository.deleteById(id);
            return true;
        } else return false;
    }

    public boolean updateRecipe(Recipe recipe, Long id) {
        if (getRecipeById(id).isPresent()) {
            recipe.setId(id);
            recipe.setDate(LocalDateTime.now());
            recipeRepository.save(recipe);
            return true;
        } else {
            return false;
        }
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return recipeRepository.findAllByNameIsContainingIgnoreCaseOrderByDateDesc(name);
    }
}
