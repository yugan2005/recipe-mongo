package guru.springframework.recipe.commandobjs;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class IngredientCommand {
  private Long id;
  private Long recipeId;
  private String description;
  private BigDecimal amount;
  private UnitOfMeasureCommand unitOfMeasure;
}
