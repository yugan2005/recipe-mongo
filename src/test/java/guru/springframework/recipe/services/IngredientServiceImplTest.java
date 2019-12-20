package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.converters.Ingredient2IngredientCommandConverter;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.repositories.IngredientRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
  IngredientRepository _ingredientRepository;

  @Mock
  Ingredient2IngredientCommandConverter _ingredient2IngredientCommandConverter;

  @InjectMocks
  IngredientServiceImpl _ingredientService;

  private Long recipeId;
  private Long ingredientId;
  private Recipe recipe;
  private String recipeDescription;
  private Ingredient ingredient1;
  private Ingredient ingredient9;

  @BeforeEach
  void setUp() {
    recipeId = 7L;
    ingredientId = 9L;
    recipeDescription = "recipe description";
    recipe = new Recipe();
    recipe.setId(recipeId);
    recipe.setDescription(recipeDescription);

    ingredient1 = new Ingredient();
    ingredient1.setId(2L);
    ingredient9 = new Ingredient();
    ingredient9.setId(ingredientId);

    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient9);

    lenient().when(_ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient9));

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredient9.getId());
    lenient().when(_ingredient2IngredientCommandConverter.convert(
        argThat(ingredient -> ingredient9.getId().equals(ingredient.getId())))).thenReturn(ingredientCommand);
  }

  @Test
  void findIngredientCommandById() {
    IngredientCommand ingredientCommand = _ingredientService.findIngredientCommandById(ingredientId);

    assertEquals(ingredientCommand.getId(), ingredientId);
    verify(_ingredient2IngredientCommandConverter, times(1)).convert(any(Ingredient.class));
    verifyNoInteractions(_recipeService);
    verify(_ingredientRepository, times(1)).findById(ingredientId);
  }

  @Test
  void saveIngredientSameId() throws Exception {
    Ingredient ingredient = new Ingredient();
    ingredient.setId(ingredientId);
    Recipe emptyRecipe = new Recipe();
    emptyRecipe.setId(recipeId);
    ingredient.setRecipe(emptyRecipe);
    Long unitOfMeasureId = 16L;
    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(unitOfMeasureId);
    ingredient.setUnitOfMeasure(unitOfMeasure);
    String description = "test description";
    ingredient.setDescription(description);

    ArgumentCaptor<Ingredient> ingredientArgumentCaptor = ArgumentCaptor.forClass(Ingredient.class);
    when(_recipeService.getRecipeById(recipeId)).thenReturn(recipe);
    when(_ingredientRepository.save(ingredientArgumentCaptor.capture())).thenReturn(ingredient);

    assertNull(ingredient.getRecipe().getDescription());

    _ingredientService.saveIngredient(ingredient);

    assertEquals(ingredientArgumentCaptor.getValue().getRecipe().getId(), recipeId);
    assertEquals(ingredientArgumentCaptor.getValue().getRecipe().getDescription(), recipeDescription);
    assertTrue(recipe.getIngredients().stream().anyMatch(ing -> ingredientId.equals(ing.getId())));
    assertEquals(recipe.getIngredients().stream().
        filter(ing -> ingredientId.equals(ing.getId())).findFirst().orElseThrow(Exception::new).
        getDescription(), description);
    assertEquals(recipe.getIngredients().size(), 2);
  }

  @Test
  void saveIngredientDifferentId() throws Exception {
    Ingredient ingredient = new Ingredient();
    Long newIngredientId = 37L;
    ingredient.setId(newIngredientId);
    Recipe emptyRecipe = new Recipe();
    emptyRecipe.setId(recipeId);
    ingredient.setRecipe(emptyRecipe);
    Long unitOfMeasureId = 16L;
    UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(unitOfMeasureId);
    ingredient.setUnitOfMeasure(unitOfMeasure);
    String description = "test with newId description";
    ingredient.setDescription(description);

    ArgumentCaptor<Ingredient> ingredientArgumentCaptor = ArgumentCaptor.forClass(Ingredient.class);
    when(_recipeService.getRecipeById(recipeId)).thenReturn(recipe);
    when(_ingredientRepository.save(ingredientArgumentCaptor.capture())).thenReturn(ingredient);

    assertNull(ingredient.getRecipe().getDescription());

    _ingredientService.saveIngredient(ingredient);

    assertEquals(ingredientArgumentCaptor.getValue().getRecipe().getId(), recipeId);
    assertEquals(ingredientArgumentCaptor.getValue().getRecipe().getDescription(), recipeDescription);
    assertTrue(recipe.getIngredients().stream().anyMatch(ing -> ingredientId.equals(ing.getId())));
    assertEquals(recipe.getIngredients().stream().
        filter(ing -> newIngredientId.equals(ing.getId())).findFirst().orElseThrow(Exception::new).
        getDescription(), description);
    assertEquals(recipe.getIngredients().size(), 3);
  }

  @Test
  void removeIngredient() {
    assertTrue(recipe.getIngredients().contains(ingredient9));
    assertEquals(ingredient9.getRecipe(), recipe);
    _ingredientService.removeIngredient(ingredient9);
    assertFalse(recipe.getIngredients().contains(ingredient9));
    assertNull(ingredient9.getRecipe());
  }
}