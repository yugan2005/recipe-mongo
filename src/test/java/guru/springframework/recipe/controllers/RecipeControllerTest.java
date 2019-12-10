package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
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
    _mockMvc.perform(get("/recipe/" + recipeId + "/show")).
        andExpect(status().isOk()).
        andExpect(view().name("recipe/show")).
        andExpect(model().attributeExists("recipe")).
        andExpect(model().attribute("recipe", recipe)).
        andExpect(model().attribute("recipe", hasProperty("id", equalTo(recipeId))));
  }

  @Test
  void saveOrUpdate() throws Exception {
    long recipeCommandId = 4L;
    String recipeCommandDescription = "some description";
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(recipeCommandId);
    recipeCommand.setDescription(recipeCommandDescription);

    ArgumentCaptor<RecipeCommand> recipeCommandArgumentCaptor = ArgumentCaptor.forClass(RecipeCommand.class);

    when(_recipeService.saveRecipeCommand(ArgumentMatchers.any(RecipeCommand.class))).
        thenReturn(recipeCommand);

    _mockMvc.perform(post("/recipe").
        contentType(MediaType.APPLICATION_FORM_URLENCODED).
        param("id", String.valueOf(recipeCommandId)).
        param("description", recipeCommandDescription)).
        andExpect(status().is3xxRedirection()).
        andExpect(view().name("redirect:/recipe/" + recipeCommandId + "/show"));

    verify(_recipeService, times(1)).saveRecipeCommand(recipeCommandArgumentCaptor.capture());

    assertEquals(recipeCommandId, recipeCommandArgumentCaptor.getValue().getId());
    assertEquals(recipeCommandDescription, recipeCommandArgumentCaptor.getValue().getDescription());

  }

  @Test
  void newRecipe() throws Exception {

    _mockMvc.perform(get("/recipe/new")).
        andExpect(status().isOk()).
        andExpect(view().name("recipe/recipeForm")).
        andExpect(model().attributeExists("recipeCommand")).
        andExpect(model().attribute("recipeCommand", Matchers.isA(RecipeCommand.class)));
  }
}