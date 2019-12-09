package guru.springframework.recipe.controllers;

import com.google.common.collect.ImmutableSet;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

  @Mock
  private RecipeService _recipeService;

  @Mock
  private Model _model;

  @InjectMocks
  private IndexController _indexController;

  @Captor
  private ArgumentCaptor<Set<Recipe>> recipeSetArgumentCaptor;

  @BeforeEach
  void setUp() {
    Recipe recipe1 = new Recipe();
    recipe1.setId(0L);
    Recipe recipe2 = new Recipe();
    recipe2.setId(1L);
    when(_recipeService.getRecipes()).thenReturn(ImmutableSet.of(recipe1, recipe2));
  }

  @Test
  void testMockMVC() throws Exception {
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(_indexController).build();

    mockMvc.perform(get("/")).
        andExpect(status().isOk()).
        andExpect(view().name("index"));
  }

  @Test
  void testIndex() {
    assertEquals(_indexController.index(_model), "index");
    verify(_model, times(1)).addAttribute(eq("recipes"), recipeSetArgumentCaptor.capture());
    verify(_recipeService, times(1)).getRecipes();

    assertEquals(recipeSetArgumentCaptor.getValue().size(), 2);
  }
}