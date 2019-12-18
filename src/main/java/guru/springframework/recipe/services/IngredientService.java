package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.domain.Ingredient;


public interface IngredientService {
  IngredientCommand findIngredientCommandById(long ingredientId);

  IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

  Ingredient saveIngredient(Ingredient ingredient);
}