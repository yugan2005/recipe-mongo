package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.converters.Ingredient2IngredientCommandConverter;
import guru.springframework.recipe.converters.IngredientCommand2IngredientConverter;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.IngredientRepository;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;


@Service
public class IngredientServiceImpl implements IngredientService {

  private final RecipeService _recipeService;
  private final IngredientRepository _ingredientRepository;
  private final Ingredient2IngredientCommandConverter _ingredient2IngredientCommandConverter;
  private final IngredientCommand2IngredientConverter _ingredientCommand2IngredientConverter;

  public IngredientServiceImpl(RecipeService recipeService, IngredientRepository ingredientRepository,
      Ingredient2IngredientCommandConverter ingredient2IngredientCommandConverter,
      IngredientCommand2IngredientConverter ingredientCommand2IngredientConverter) {
    _recipeService = recipeService;
    _ingredientRepository = ingredientRepository;
    _ingredient2IngredientCommandConverter = ingredient2IngredientCommandConverter;
    _ingredientCommand2IngredientConverter = ingredientCommand2IngredientConverter;
  }

  @Override
  public IngredientCommand findIngredientCommandById(long ingredientId) {
    return _ingredientRepository.findById(ingredientId).
        map(_ingredient2IngredientCommandConverter::convert).orElse(null);
  }

  @Override
  public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
    Ingredient ingredient = _ingredientCommand2IngredientConverter.convert(ingredientCommand);

    if (ingredient == null) {
      return null;
    }

    ingredient = saveIngredient(ingredient);
    return _ingredient2IngredientCommandConverter.convert(ingredient);
  }

  @Override
  public Ingredient saveIngredient(@NotNull Ingredient ingredient) {
    Recipe recipe = _recipeService.getRecipeById(ingredient.getRecipe().getId());
    Ingredient savedIngredient = recipe.getIngredients()
        .stream()
        .filter(ing -> ing.getId().equals(ingredient.getId()))
        .findFirst()
        .orElse(ingredient);

    savedIngredient.setRecipe(recipe);
    savedIngredient.setAmount(ingredient.getAmount());
    savedIngredient.setDescription(ingredient.getDescription());
    savedIngredient.setUnitOfMeasure(ingredient.getUnitOfMeasure());

    savedIngredient = _ingredientRepository.save(savedIngredient);
    recipe.addIngredient(savedIngredient);

    return savedIngredient;
  }
}