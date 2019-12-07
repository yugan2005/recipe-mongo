package guru.springframework.recipe.controllers;

import guru.springframework.recipe.domain.Recipe;
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
class RecipeControllerTest {

  @Mock
  RecipeService _recipeService;

  @InjectMocks
  RecipeController _recipeController;

  private MockMvc _mockMvc;

  @BeforeEach
  void setUp() {
    _mockMvc = MockMvcBuilders.standaloneSetup(_recipeController).build();
  }

  @Test
  void showRecipe() throws Exception {
    long recipeId = 3L;
    Recipe recipe = new Recipe();
    recipe.setId(recipeId);
    when(_recipeService.getRecipeById(recipeId)).thenReturn(recipe);
    _mockMvc.perform(get("/recipe/show/" + recipeId)).
        andExpect(status().isOk()).
        andExpect(view().name("recipe/show")).
        andExpect(model().attributeExists("recipe")).
        andExpect(model().attribute("recipe", recipe)).
        andExpect(model().attribute("recipe", hasProperty("id", equalTo(recipeId))));
  }
}