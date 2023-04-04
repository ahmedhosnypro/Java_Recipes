package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ApiController {
    List<Recipe> recipes = new CopyOnWriteArrayList<>();

    @GetMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        if (id < recipes.size()) {
            return new ResponseEntity<>(recipes.get(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/api/recipe/new")
    public ResponseEntity<Map<String, Integer>> setRecipe(@RequestBody Recipe recipe) {
        recipes.add(recipe);
        return new ResponseEntity<>(Map.of("id", recipes.size() - 1), HttpStatus.OK);
    }
}
