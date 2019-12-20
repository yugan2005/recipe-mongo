package guru.springframework.recipe.controllers;

import com.google.common.collect.Lists;
import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.commandobjs.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.IngredientService;
import guru.springframework.recipe.services.RecipeService;
import guru.springframework.recipe.services.UnitOfMeasureService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class IngredientsControllerTest {

  @Mock
  RecipeService _recipeService;

  @Mock
  IngredientService _ingredientService;

  @Mock
  UnitOfMeasureService _unitOfMeasureService;

  @InjectMocks
  IngredientsController _ingredientsController;

  MockMvc _mockMvc;

  Long recipeId;

  @BeforeEach
  void setUp() {
    _mockMvc = MockMvcBuilders.standaloneSetup(_ingredientsController).build();
    recipeId = 1L;
  }

  @Test
  void listIngredients() throws Exception {
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(recipeId);

    when(_recipeService.getRecipeCommandById(argThat(id -> recipeId.equals(id)))).thenReturn(recipeCommand);

    _mockMvc.perform(get("/recipe/" + recipeId + "/ingredients")).
        andExpect(status().isOk()).
        andExpect(view().name("/recipe/ingredient/list")).
        andExpect(model().attributeExists("recipe")).
        andExpect(model().attribute("recipe", hasProperty("id", equalTo(recipeId))));

    verify(_recipeService, times(1)).getRecipeCommandById(recipeId);
  }

  @Test
  void showIngredient() throws Exception {

    Long ingredientId = 7L;
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredientId);

    when(_ingredientService.findIngredientCommandById(ingredientId)).thenReturn(ingredientCommand);

    _mockMvc.perform(get("/recipe/ingredient/" + ingredientId + "/show")).
        andExpect(status().isOk()).
        andExpect(view().name("/recipe/ingredient/show")).
        andExpect(model().attributeExists("ingredientCommand")).
        andExpect(model().attribute("ingredientCommand", hasProperty("id", equalTo(ingredientId))));
  }

  @Test
  void updateIngredient() throws Exception {
    Long ingredientId = 7L;
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredientId);

    when(_unitOfMeasureService.getUnitOfMeasureCommands()).
        thenReturn(Lists.newArrayList(new UnitOfMeasureCommand(), new UnitOfMeasureCommand()));
    when(_ingredientService.findIngredientCommandById(ingredientId)).thenReturn(ingredientCommand);

    _mockMvc.perform(get("/recipe/ingredient/{ingredientId}/update", ingredientId)).
        andExpect(status().isOk()).
        andExpect(view().name("/recipe/ingredient/ingredientForm")).
        andExpect(model().attributeExists("ingredientCommand")).
        andExpect(model().attributeExists("unitOfMeasureCommands")).
        andExpect(model().attribute("ingredientCommand", hasProperty("id", equalTo(ingredientId)))).
        andExpect(model().attribute("unitOfMeasureCommands", hasSize(2)));
  }

  @Test
  void createOrUpdate() throws Exception {
    String description = "test description";
    String amount = "4.0";
    String uomId = "2";
    String id = "7";
    String recipeId = "9";

    ArgumentCaptor<IngredientCommand> ingredientCommandArgumentCaptor =
        ArgumentCaptor.forClass(IngredientCommand.class);

    IngredientCommand ingredientCom = new IngredientCommand();
    ingredientCom.setId(Long.valueOf(id));
    ingredientCom.setRecipeId(Long.valueOf(recipeId));
    ingredientCom.setDescription(description);
    ingredientCom.setAmount(BigDecimal.valueOf(Double.valueOf(amount)));
    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(Long.valueOf(uomId));
    ingredientCom.setUnitOfMeasure(unitOfMeasureCommand);

    when(_ingredientService.saveIngredientCommand(ingredientCommandArgumentCaptor.capture())).thenReturn(ingredientCom);

    _mockMvc.perform(post("/recipe/ingredient").
        contentType(MediaType.APPLICATION_FORM_URLENCODED).
        param("description", description).
        param("amount", amount).
        param("unitOfMeasure.id", uomId).
        param("id", id).
        param("recipeId", recipeId)).
        andExpect(status().is3xxRedirection()).
        andExpect(view().name("redirect:/recipe/ingredient/" + id + "/show"));

    assertEquals(ingredientCom.getId(), ingredientCommandArgumentCaptor.getValue().getId());
    assertEquals(ingredientCom.getAmount(), ingredientCommandArgumentCaptor.getValue().getAmount());
    assertEquals(ingredientCom.getDescription(), ingredientCommandArgumentCaptor.getValue().getDescription());
    assertEquals(ingredientCom.getRecipeId(), ingredientCommandArgumentCaptor.getValue().getRecipeId());
    assertEquals(ingredientCom.getUnitOfMeasure().getId(),
        ingredientCommandArgumentCaptor.getValue().getUnitOfMeasure().getId());
  }

  @Test
  void createIngredient() throws Exception {
    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
    ingredientCommand.setRecipeId(recipeId);

    when(_unitOfMeasureService.getUnitOfMeasureCommands()).
        thenReturn(Lists.newArrayList(new UnitOfMeasureCommand(), new UnitOfMeasureCommand()));

    _mockMvc.perform(get("/recipe/{recipeId}/ingredient/new", recipeId)).
        andExpect(status().isOk()).
        andExpect(view().name("/recipe/ingredient/ingredientForm")).
        andExpect(model().attributeExists("ingredientCommand")).
        andExpect(model().attributeExists("unitOfMeasureCommands")).
        andExpect(model().attribute("ingredientCommand", hasProperty("id", nullValue()))).
        andExpect(model().attribute("ingredientCommand", hasProperty("recipeId", equalTo(recipeId)))).
        andExpect(model().attribute("unitOfMeasureCommands", hasSize(2)));
  }

  @Test
  void delete() throws Exception {
    Long ingredientId = 31L;
    Ingredient ingredient = new Ingredient();
    ingredient.setId(ingredientId);
    Recipe recipe = new Recipe();
    recipe.setId(recipeId);
    recipe.addIngredient(ingredient);
    ingredient.setRecipe(recipe);

    when(_ingredientService.findIngredientById(ingredientId)).thenReturn(ingredient);

    _mockMvc.perform(get("/recipe/ingredient/{ingredientId}/delete", ingredientId)).
        andExpect(status().is3xxRedirection()).
        andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredients"));

    verify(_ingredientService, times(1)).findIngredientById(ingredientId);
    verify(_ingredientService, times(1)).removeIngredient(ingredient);
  }
}