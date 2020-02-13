package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.converters.Recipe2RecipeCommandConverter;
import guru.springframework.recipe.converters.RecipeCommand2RecipeConverter;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.exceptions.ControllerExceptionHandler;
import guru.springframework.recipe.exceptions.NotFoundException;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.services.RecipeService;
import guru.springframework.recipe.services.RecipeServiceImpl;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
  RecipeRepository recipeRepository;

  @Mock
  RecipeCommand2RecipeConverter _recipeCommand2RecipeConverter;

  @Mock
  Recipe2RecipeCommandConverter _recipe2RecipeCommandConverter;

  private MockMvc _mockMvc;
  private Long recipeId;
  private String recipeDescription;
  private String updatedRecipeDescription;


  @BeforeEach
  void setUp() {

    RecipeService recipeService =
        new RecipeServiceImpl(recipeRepository, _recipeCommand2RecipeConverter, _recipe2RecipeCommandConverter);
    RecipeController recipeController = new RecipeController(recipeService);
    _mockMvc = MockMvcBuilders.standaloneSetup(recipeController).
        setControllerAdvice(ControllerExceptionHandler.class).
        build();

    recipeId = 7L;
    recipeDescription = "mock recipe recipeCommand description";
    updatedRecipeDescription = "mock updated recipe recipeCommand description";
    Recipe recipe = new Recipe();
    recipe.setId(7L);
    recipe.setDescription(recipeDescription);

    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(recipeId);
    recipeCommand.setDescription(recipeDescription);

    Recipe updatedRecipe = new Recipe();
    updatedRecipe.setId(recipeId);
    updatedRecipe.setDescription(updatedRecipeDescription);

    RecipeCommand updatedRecipeCommand = new RecipeCommand();
    updatedRecipeCommand.setId(recipeId);
    updatedRecipeCommand.setDescription(updatedRecipeDescription);

    lenient().doReturn(recipe).when(_recipeCommand2RecipeConverter).convert(
        argThat(inputRecipeCommand -> recipeDescription.equals(inputRecipeCommand.getDescription())));
    lenient().doReturn(updatedRecipe).when(_recipeCommand2RecipeConverter).convert(
        argThat(inputRecipeCommand -> updatedRecipeDescription.equals(inputRecipeCommand.getDescription())));

    lenient().doReturn(recipeCommand).when(_recipe2RecipeCommandConverter).convert(
        argThat(inputRecipe -> recipeDescription.equals(inputRecipe.getDescription())));
    lenient().doReturn(updatedRecipeCommand).when(_recipe2RecipeCommandConverter).convert(
        argThat(inputRecipe -> updatedRecipeDescription.equals(inputRecipe.getDescription())));

    lenient().when(recipeRepository.save(recipe)).thenReturn(recipe);
    lenient().when(recipeRepository.save(updatedRecipe)).thenReturn(updatedRecipe);

    lenient().when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
  }

  @Test
  void showRecipe() throws Exception {
    long recipeId = 3L;
    Recipe recipe = new Recipe();
    recipe.setId(recipeId);
    when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
    _mockMvc.perform(get("/recipe/" + recipeId + "/show")).
        andExpect(status().isOk()).
        andExpect(view().name("recipe/show")).
        andExpect(model().attributeExists("recipe")).
        andExpect(model().attribute("recipe", recipe)).
        andExpect(model().attribute("recipe", hasProperty("id", equalTo(recipeId))));
  }

  @Test
  void saveOrUpdate() throws Exception {
    ArgumentCaptor<RecipeCommand> recipeCommandArgumentCaptor = ArgumentCaptor.forClass(RecipeCommand.class);
    ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

    _mockMvc.perform(post("/recipe").
        contentType(MediaType.APPLICATION_FORM_URLENCODED).
        param("id", String.valueOf(recipeId)).
        param("description", recipeDescription).
        param("directions", "not empty direction")).
        andExpect(status().is3xxRedirection()).
        andExpect(view().name("redirect:/recipe/" + recipeId + "/show"));

    verify(_recipeCommand2RecipeConverter, times(1)).convert(recipeCommandArgumentCaptor.capture());
    verify(recipeRepository, times(1)).save(recipeArgumentCaptor.capture());

    verify(_recipe2RecipeCommandConverter, times(1)).convert(recipeArgumentCaptor.capture());

    assertEquals(recipeId, recipeCommandArgumentCaptor.getValue().getId());
    assertEquals(recipeDescription, recipeCommandArgumentCaptor.getValue().getDescription());
    assertEquals(recipeId, recipeArgumentCaptor.getAllValues().get(0).getId());
    assertEquals(recipeDescription, recipeArgumentCaptor.getAllValues().get(0).getDescription());
    assertEquals(recipeId, recipeArgumentCaptor.getAllValues().get(1).getId());
    assertEquals(recipeDescription, recipeArgumentCaptor.getAllValues().get(1).getDescription());
  }

  @Test
  void newRecipe() throws Exception {

    _mockMvc.perform(get("/recipe/new")).
        andExpect(status().isOk()).
        andExpect(view().name("recipe/recipeForm")).
        andExpect(model().attributeExists("recipeCommand")).
        andExpect(model().attribute("recipeCommand", Matchers.isA(RecipeCommand.class)));
  }

  @Test
  void update() throws Exception {

    _mockMvc.perform(get("/recipe/" + recipeId + "/update")).
        andExpect(status().isOk()).
        andExpect(view().name("recipe/recipeForm")).
        andExpect(model().attributeExists("recipeCommand")).
        andExpect(model().attribute("recipeCommand", hasProperty("id", equalTo(recipeId))));

    verify(recipeRepository, times(1)).findById(recipeId);
    verify(_recipe2RecipeCommandConverter).convert(argThat(recipe -> recipeId.equals(recipe.getId())));
  }

  @Test
  void testDelete() throws Exception {

    _mockMvc.perform(get("/recipe/" + recipeId +"/delete")).
        andExpect(status().is3xxRedirection()).
        andExpect(view().name("redirect:/"));

    verify(recipeRepository, times(1)).deleteById(recipeId);
  }

  @Test
  void testGetRecipeNotFound() throws Exception {
    long notFoundId = 13L;
    when(recipeRepository.findById(notFoundId)).thenThrow(NotFoundException.class);

    _mockMvc.perform(get("/recipe/" + notFoundId + "/show")).
        andExpect(status().isNotFound()).
        andExpect(view().name("404error"));
  }

  @Test
  void testNumberFormatException() throws Exception {
    _mockMvc.perform(get("/recipe/abc/show")).
        andExpect(status().isBadRequest()).
        andExpect(view().name("400error"));
  }

  @Test
  void testPostValidationFail() throws Exception {
    _mockMvc.perform(post("/recipe").
        contentType(MediaType.APPLICATION_FORM_URLENCODED).
        param("cookTime", "0")).
        andExpect(model().attributeHasFieldErrorCode("recipeCommand", "cookTime", "Min")).
        andExpect(model().attributeHasFieldErrorCode("recipeCommand", "description", "NotBlank"));
  }
}