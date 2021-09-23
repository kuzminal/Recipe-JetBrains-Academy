package recipes.service;

import org.springframework.stereotype.Service;
import recipes.domain.Recipe;
import recipes.domain.User;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Recipe saveRecipe(Recipe recipe, String userName) {
        Optional<User> user = userRepository.findByEmail(userName);
        user.ifPresent(recipe::setAuthor);
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public boolean deleteById(Long id, String userName) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        Optional<User> user = userRepository.findByEmail(userName);
        if (recipe.isEmpty()) {
            return false;
        } else if (user.isPresent() && user.get() == recipe.get().getAuthor()) {
            recipeRepository.deleteById(id);
            return true;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean updateRecipe(Recipe recipe, Long id, String userName) {
        Optional<User> user = userRepository.findByEmail(userName);
        Optional<Recipe> recipeFound = getRecipeById(id);
        if (recipeFound.isEmpty()) {
            return false;
        } else if (user.isPresent() && user.get() == recipeFound.get().getAuthor()) {
            recipe.setId(id);
            recipe.setDate(LocalDateTime.now());
            recipe.setAuthor(user.get());
            recipeRepository.save(recipe);
            return true;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return recipeRepository.findAllByNameIsContainingIgnoreCaseOrderByDateDesc(name);
    }
}
