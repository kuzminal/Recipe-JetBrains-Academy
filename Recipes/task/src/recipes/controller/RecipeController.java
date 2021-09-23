package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.domain.Recipe;
import recipes.service.RecipeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.*;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable @Max(Long.MAX_VALUE) @Min(0) Long id) {
        return recipeService.getRecipeById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    @GetMapping("/search")
    public List<Recipe> search(@RequestParam Optional<String> category,
                               @RequestParam Optional<String> name) {
        if ((category.isPresent() && name.isPresent()) ||
                (category.isEmpty() && name.isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (category.isPresent()) {
            return recipeService.findByCategory(category.get());
        } else {
            return recipeService.findByName(name.get());
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> saveRecipe(@RequestBody @Valid Recipe recipe, HttpServletRequest request) {
        Recipe recipe1 = recipeService.saveRecipe(recipe, request.getUserPrincipal().getName());
        return new ResponseEntity<>(Map.of("id", recipe1.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipeById(@PathVariable @Max(Long.MAX_VALUE) @Min(0) Long id, HttpServletRequest request) {
        try {
            if (!recipeService.deleteById(id, request.getUserPrincipal().getName())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, @RequestBody @Valid Recipe
            recipe, HttpServletRequest request) {
        try {
            if (!recipeService.updateRecipe(recipe, id, request.getUserPrincipal().getName())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
