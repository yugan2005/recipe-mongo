package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class Ingredient2IngredientCommandConverter implements Converter<Ingredient, IngredientCommand> {

  private final UnitOfMeasure2UnitOfMeasureCommandConverter unitOfMeasure2UnitOfMeasureCommandConverter;

  public Ingredient2IngredientCommandConverter(
      UnitOfMeasure2UnitOfMeasureCommandConverter unitOfMeasure2UnitOfMeasureCommandConverter) {
    this.unitOfMeasure2UnitOfMeasureCommandConverter = unitOfMeasure2UnitOfMeasureCommandConverter;
  }

  @Nullable
  @Synchronized
  @Override
  public IngredientCommand convert(@Nullable Ingredient source) {
    if (source == null) {
      return null;
    }

    final IngredientCommand ingredientCommand = new IngredientCommand();

    ingredientCommand.setUnitOfMeasure(unitOfMeasure2UnitOfMeasureCommandConverter.
        convert(source.getUnitOfMeasure()));
    ingredientCommand.setAmount(source.getAmount());
    ingredientCommand.setDescription(source.getDescription());
    ingredientCommand.setId(source.getId());

    if (source.getRecipe() != null) {
      ingredientCommand.setRecipeId(source.getRecipe().getId());
    }

    return ingredientCommand;
  }
}
