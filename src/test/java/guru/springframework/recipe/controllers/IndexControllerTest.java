package guru.springframework.recipe.controllers;

import com.google.common.collect.ImmutableSet;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class IndexControllerTest {

  private IndexController _indexController;
  @Mock
  private RecipeService _recipeService;

  @Mock
  private Model _model;

  @Captor
  private ArgumentCaptor<Set<Recipe>> recipeSetArgumentCaptor;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    _indexController = new IndexController(_recipeService);
    Recipe recipe1 = new Recipe();
    recipe1.setId(0L);
    Recipe recipe2 = new Recipe();
    recipe2.setId(1L);
    when(_recipeService.getRecipes()).thenReturn(ImmutableSet.of(recipe1, recipe2));
  }

  @Test
  public void testMockMVC() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(_indexController).build();

    mockMvc.perform(get("/")).
        andExpect(status().isOk()).
        andExpect(view().name("index"));
  }

  @Test
  public void testIndex() {
    Assert.assertEquals(_indexController.index(_model), "index");
    verify(_model, times(1)).addAttribute(eq("recipes"), recipeSetArgumentCaptor.capture());
    verify(_recipeService, times(1)).getRecipes();

    Assert.assertEquals(recipeSetArgumentCaptor.getValue().size(), 2);
  }
}