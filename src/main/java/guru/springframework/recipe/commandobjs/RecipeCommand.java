package guru.springframework.recipe.commandobjs;

import guru.springframework.recipe.domain.Difficulity;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;


@Data
@NoArgsConstructor
public class RecipeCommand {

  private Long id;

  @NotBlank
  @Size(min = 3, max = 255)
  private String description;

  @Min(1)
  @Max(999)
  private Integer prepTime;

  @Min(1)
  @Max(999)
  private Integer cookTime;

  @Min(1)
  @Max(100)
  private Integer servings;
  private String source;

  @URL
  private String url;

  @NotBlank
  private String directions;
  private NotesCommand notes;
  private Set<IngredientCommand> ingredients = new HashSet<>();
  private Difficulity difficulity;
  private Set<CategoryCommand> categories = new HashSet<>();
}
