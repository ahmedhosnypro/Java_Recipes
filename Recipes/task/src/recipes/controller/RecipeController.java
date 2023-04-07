package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import recipes.dto.RecipeDTO;
import recipes.model.Recipe;
import recipes.security.SecurityUserDetails;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller for recipes
 * <p>
 * Provides methods for <ul>
 * <li>saving a new recipe</li>
 * <li>updating an existing recipe</li>
 * <li>getting a recipe by id</li>
 * <li>searching recipes by recipe's category or name</li>
 * </ul>
 * <p>
 *
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * save a new recipe
     * <p>
     * The recipe must have a name, category, description, ingredients and cooking steps
     * <br>
     * Owner of a recipe is a user who saves a recipe
     * </p>
     *
     * @param securityUserDetails user who saves a recipe
     * @param recipeDTO           recipe to save
     * @return <ul>
     * <li>200 OK with a recipe id if a recipe saved</li>
     * <li>400 Bad Request if a recipe is invalid</li>
     * </ul>
     */
    @PostMapping(path = "/api/recipe/new")
    public ResponseEntity<Map<String, Long>> sava(@AuthenticationPrincipal SecurityUserDetails securityUserDetails,
                                                  @Valid @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeDTO.toRecipe();
        recipe.setUser(securityUserDetails.getUser());
        recipeService.save(recipe);
        return new ResponseEntity<>(Map.of("id", recipe.getId()), HttpStatus.OK);
    }


    /**
     * update a recipe
     * <p>
     * The recipe must have a name, category, description, ingredients and cooking steps
     * <br>
     * The user who updates the recipe must be the recipe owner
     * <br>
     *
     * @param securityUserDetails user who updates a recipe, must be the same as a recipe owner
     * @param id                  id of a recipe to update
     * @param recipeDTO           recipe to update
     * @return <ul>
     * <li>200 OK if a recipe updated</li>
     * <li>403 Forbidden if a recipe owner is not the same as a user who updates a recipe</li>
     * <li>404 Not Found if a recipe not found</li>
     * <li>400 Bad Request if a recipe is invalid</li>
     * </ul>
     */
    @PutMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Object> update(@AuthenticationPrincipal SecurityUserDetails securityUserDetails,
                                         @PathVariable long id, @Valid @RequestBody RecipeDTO recipeDTO) {
        if (recipeService.existsById(id)) {
            var optRecipe = recipeService.findById(id);
            if (optRecipe.isPresent() &&
                    (!securityUserDetails.getUser().getId().equals(optRecipe.get().getUser().getId()))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            Recipe recipe = recipeDTO.toRecipe();
            recipe.setId(id);
            recipe.setUser(securityUserDetails.getUser());
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
     * @return <ul>
     * <li>200 OK with a recipe if a recipe found</li>
     * <li>404 Not Found if a recipe not found</li>
     * </ul>
     */
    @GetMapping(path = "/api/recipe/{id}")
    public ResponseEntity<RecipeDTO> findById(@PathVariable int id) {
        var optRecipe = recipeService.findById(id);
        return optRecipe.map(recipe -> new ResponseEntity<>(RecipeDTO.fromRecipe(recipe),
                        HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * search recipes by category or name
     * <p>
     * search by only one of category or name
     *     <ul>
     *     <li>If both category and name are provided, a bad request will be returned</li>
     *     <li>If both category and name are null, a bad request will be returned</li>
     *     <li>If a request is valid, a list of recipes will be returned</li>
     *     </ul>
     * </p>
     *
     * @param category category of recipes to search
     * @param name     name of recipes to search
     * @return <ul>
     *     <li>200 OK with a list of recipes if a recipes found</li>
     *     <li>400 Bad Request if a request is invalid</li>
     * </ul>
     */
    @GetMapping(path = "/api/recipe/search")
    public ResponseEntity<List<RecipeDTO>> search(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        if (category != null && name != null) {
            return ResponseEntity.badRequest().build();
        } else if (category != null) {
            return new ResponseEntity<>(
                    recipeService.findByCategory(category)
                            .stream()
                            .map(RecipeDTO::fromRecipe)
                            .toList(),
                    HttpStatus.OK);
        } else if (name != null) {
            return new ResponseEntity<>(
                    recipeService.findByNameContainingIgnoreCaseOrderByDateDesc(name)
                            .stream()
                            .map(RecipeDTO::fromRecipe)
                            .toList(),
                    HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * delete recipe by id
     * <p>
     * The user who deletes the recipe must be the recipe owner
     * </p>
     *
     * @param securityUserDetails user who deletes a recipe, must be the same as a recipe owner
     * @param id                  id of recipe to delete
     * @return <ul>
     * <li>200 (OK) if a recipe deleted</li>
     * <li>403 (Forbidden) if a recipe owner is not the same as a user who deletes a recipe</li>
     * <li>(404) Not Found if a recipe not found</li>
     * </ul>
     */
    @DeleteMapping(path = "/api/recipe/{id}")
    public ResponseEntity<Object> deleteRecipe(@AuthenticationPrincipal SecurityUserDetails securityUserDetails,
                                               @PathVariable int id) {
        if (recipeService.existsById(id)) {
            var optRecipe = recipeService.findById(id);
            if (optRecipe.isPresent() &&
                    (!securityUserDetails.getUser().getId().equals(optRecipe.get().getUser().getId()))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            recipeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}