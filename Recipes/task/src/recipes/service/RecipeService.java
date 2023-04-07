package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }

    public Optional<Recipe> findById(long id) {
        return recipeRepository.findById(id);
    }

    public boolean existsById(long id) {
        return recipeRepository.existsById(id);
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
