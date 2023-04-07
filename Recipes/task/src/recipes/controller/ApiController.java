package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class ApiController {
    private final RecipeService recipeService;

    public ApiController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * save a new recipe
     *
     * @param recipe recipe to save
     * @return 200 OK with an id of saved recipe
     */
    @PostMapping(path = "/api/recipe/new")
    public ResponseEntity<Map<String, Long>> sava(@Valid @RequestBody Recipe recipe) {
        var savesRecipe = recipeService.save(recipe);
        return new ResponseEntity<>(Map.of("id", savesRecipe.getId()), HttpStatus.OK);
    }


    /**
     * update a recipe
     * @param id id of a recipe to update
     * @param recipe recipe to update
     * @return 200 OK if a recipe updated, 404 Not Found if a recipe not found, 400 Bad Request if a recipe is invalid
     */
    @PutMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody Recipe recipe) {
        if (recipeService.existsById(id)) {
            recipe.setId(id);
            recipeService.save(recipe);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * get a recipe by id
     *
     * @param id id of a recipe to get
     * @return 200 OK with a recipe if a recipe found, 404 Not Found if a recipe not found
     */
    @GetMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Recipe> findById(@PathVariable int id) {
        var optRecipe = recipeService.findById(id);
        return optRecipe.map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * search recipes by category or name
     *
     * @param category category of recipes to search
     * @param name name of recipes to search
     * @return 200 OK with a list of recipes if a recipes found, 400 Bad Request if a request is invalid
     */
    @GetMapping(path = "/api/recipe/search")
    public ResponseEntity<Object> search(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        if (category != null && name != null) {
            return ResponseEntity.badRequest().build();
        } else if (category != null) {
            return new ResponseEntity<>(recipeService.findByCategory(category), HttpStatus.OK);
        } else if (name != null) {
            return new ResponseEntity<>(recipeService.findByNameContainingIgnoreCaseOrderByDateDesc(name), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * delete recipe by id
     *
     * @param id id of recipe to delete
     * @return 200 OK if a recipe deleted, 404 Not Found if a recipe not found
     */
    @DeleteMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable int id) {
        if (recipeService.existsById(id)) {
            recipeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}