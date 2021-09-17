package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.domain.Recipe;

import java.util.*;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    List<Recipe> resipes = new ArrayList<>();

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable int id) {
        if (id < resipes.size() && resipes.get(id) != null) {
            return resipes.get(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> saveRecipe(@RequestBody Recipe recipe) {
        resipes.add(recipe);
        return new ResponseEntity<>(Map.of("id", resipes.indexOf(recipe)), HttpStatus.OK);
    }
}
