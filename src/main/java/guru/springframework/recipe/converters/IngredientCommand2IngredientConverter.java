package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class IngredientCommand2IngredientConverter implements Converter<IngredientCommand, Ingredient> {

  private final UnitOfMeasureCommand2UnitOfMeasureConverter unitOfMeasureCommand2UnitOfMeasureConverter;

  public IngredientCommand2IngredientConverter(
      UnitOfMeasureCommand2UnitOfMeasureConverter unitOfMeasureCommand2UnitOfMeasureConverter) {
    this.unitOfMeasureCommand2UnitOfMeasureConverter = unitOfMeasureCommand2UnitOfMeasureConverter;
  }

  @Synchronized
  @Nullable
  @Override
  public Ingredient convert(@Nullable IngredientCommand source) {
    if (source == null) {
      return null;
    }

    final Ingredient ingredient = new Ingredient();
    ingredient.setAmount(source.getAmount());
    ingredient.setDescription(source.getDescription());
    ingredient.setUnitOfMeasure(unitOfMeasureCommand2UnitOfMeasureConverter.
        convert(source.getUnitOfMeasure()));
    ingredient.setId(source.getId());
    return ingredient;
  }
}
