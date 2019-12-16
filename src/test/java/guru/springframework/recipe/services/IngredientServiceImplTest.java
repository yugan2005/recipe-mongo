package guru.springframework.recipe.services;

import com.google.common.collect.ImmutableSet;
import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.converters.Ingredient2IngredientCommandConverter;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

  @Mock
  RecipeService _recipeService;

  @Mock
  Ingredient2IngredientCommandConverter _ingredient2IngredientCommandConverter;

  @InjectMocks
  IngredientServiceImpl _ingredientService;

  private Long recipeId;
  private Long ingredientId;

  @BeforeEach
  void setUp() {
    recipeId = 7L;
    ingredientId = 9L;

    Recipe recipe = new Recipe();
    recipe.setId(recipeId);

    Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(2L);
    Ingredient ingredient9 = new Ingredient();
    ingredient9.setId(ingredientId);

    Set<Ingredient> ingredients = ImmutableSet.of(ingredient1, ingredient9);
    recipe.setIngredients(ingredients);

    when(_recipeService.getRecipeById(recipeId)).thenReturn(recipe);

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredient9.getId());
    when(_ingredient2IngredientCommandConverter.convert(
        argThat(ingredient -> ingredient9.getId().equals(ingredient.getId())))).thenReturn(ingredientCommand);
  }

  @Test
  void findByRecipeIdAndIngredientId() {
    IngredientCommand ingredientCommand = _ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);

    assertEquals(ingredientCommand.getId(), ingredientId);
    verify(_ingredient2IngredientCommandConverter, times(1)).convert(any(Ingredient.class));
    verify(_recipeService, times(1)).getRecipeById(recipeId);
  }
}