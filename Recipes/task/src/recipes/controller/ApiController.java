package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.model.Recipe;

@RestController
public class ApiController {
    Recipe recipe = new Recipe();

    @GetMapping(path = "/api/recipe")
    public ResponseEntity<Recipe> getRecipe() {
        return new ResponseEntity<>(recipe, recipe.getName() == null
                ? HttpStatus.NOT_FOUND
                : HttpStatus.OK);
    }

    @PostMapping(path = "/api/recipe")
    public void setRecipe(@RequestBody Recipe recipe) {
        this.recipe = recipe;
    }
}
