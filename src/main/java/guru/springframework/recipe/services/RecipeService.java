package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import java.util.Set;


public interface RecipeService {

  Set<Recipe> getRecipes();

  Recipe getRecipeById(Long id);

  Recipe saveRecipe(Recipe recipe);

  RecipeCommand saveRecipeCommand(RecipeCommand detachedRecipeCommand);

  RecipeCommand getRecipeCommandById(Long id);

  void deleteRecipeById(Long recipeId);
}
