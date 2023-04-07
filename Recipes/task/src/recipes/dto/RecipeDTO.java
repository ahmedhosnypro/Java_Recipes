package recipes.dto;

import lombok.*;
import recipes.model.Recipe;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static recipes.dto.Dto.modelMapper;

/**
 * RecipeDTO class`
 * @author Ahmed Hosny
 * @version 1.0
 * @since 2023-04-07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecipeDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String category;
    private LocalDateTime date;

    @NotBlank
    private String description;

    @NotEmpty
    @Size(min = 1)
    private List<String> ingredients;

    @NotEmpty
    @Size(min = 1)
    private List<String> directions;

    /**
     * converts Recipe to RecipeDTO
     * @param recipe Recipe to be converted
     * @return RecipeDTO
     */
    public static RecipeDTO fromRecipe(Recipe recipe) {
        return modelMapper().map(recipe, RecipeDTO.class);
    }

    /**
     * converts RecipeDTO to Recipe
     * @return Recipe
     */
    public Recipe toRecipe() {
        return modelMapper().map(this, Recipe.class);
    }
}


