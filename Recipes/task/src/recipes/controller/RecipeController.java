package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.domain.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    @PostMapping("/new")
    public ResponseEntity<?> saveRecipe(@RequestBody @Valid Recipe recipe) throws IOException {
        try (FileWriter fw = new FileWriter(new File("tmp.txt"))) {
            fw.write(recipe.toString());
        }
        Recipe recipe1 = recipeService.saveRecipe(recipe);
        return new ResponseEntity<>(Map.of("id", recipe1.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipeById(@PathVariable @Max(Long.MAX_VALUE) @Min(0) Long id) {
        if (recipeService.deleteById(id) != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
