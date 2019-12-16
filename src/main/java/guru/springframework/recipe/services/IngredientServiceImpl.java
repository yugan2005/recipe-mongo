package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.converters.Ingredient2IngredientCommandConverter;
import guru.springframework.recipe.domain.Recipe;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;


@Service
public class IngredientServiceImpl implements IngredientService {

  private final RecipeService _recipeService;
  private final Ingredient2IngredientCommandConverter _ingredient2IngredientCommandConverter;

  public IngredientServiceImpl(RecipeService recipeService,
      Ingredient2IngredientCommandConverter ingredient2IngredientCommandConverter) {
    _recipeService = recipeService;
    _ingredient2IngredientCommandConverter = ingredient2IngredientCommandConverter;
  }

  @Override
  public IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {
    return Optional.ofNullable(_recipeService.getRecipeById(recipeId))
        .map(Recipe::getIngredients)
        .map(Set::stream)
        .flatMap(ingredientStream -> ingredientStream.filter(
            ingredient -> (ingredient.getId() != null) && (ingredient.getId().equals(ingredientId))).findFirst())
        .map(_ingredient2IngredientCommandConverter::convert)
        .orElse(null);
  }
}