package guru.springframework.recipe.commandobjs;

import guru.springframework.recipe.domain.Difficulity;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RecipeCommand {
  private Long id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;
  private String directions;
  private NotesCommand notes;
  private Set<IngredientCommand> ingredients = new HashSet<>();
  private Difficulity difficulity;
  private Set<CategoryCommand> categories = new HashSet<>();
}
