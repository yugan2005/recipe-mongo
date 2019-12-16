package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class IngredientsControllerTest {

  @Mock
  RecipeService _recipeService;

  @InjectMocks
  IngredientsController _ingredientsController;

  MockMvc _mockMvc;

  @BeforeEach
  void setUp() {
    _mockMvc = MockMvcBuilders.standaloneSetup(_ingredientsController).build();
  }

  @Test
  void listIngredients() throws Exception {
    Long recipeId= 1L;
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
}