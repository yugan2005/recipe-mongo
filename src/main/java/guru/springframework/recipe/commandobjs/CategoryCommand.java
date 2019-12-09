package guru.springframework.recipe.commandobjs;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CategoryCommand {
  private Long id;
  private String description;
}
