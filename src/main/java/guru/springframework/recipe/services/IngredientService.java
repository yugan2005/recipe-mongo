package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.IngredientCommand;


public interface IngredientService {
  IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId);
}