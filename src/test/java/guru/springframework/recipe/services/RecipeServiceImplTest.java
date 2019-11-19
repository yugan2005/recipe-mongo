package guru.springframework.recipe.services;

import com.google.common.collect.ImmutableList;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


public class RecipeServiceImplTest {

  RecipeServiceImpl recipeService;
  Recipe recipe;

  @Mock
  RecipeRepository recipeRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository);
    recipe = new Recipe();
    when(recipeRepository.findAll()).thenReturn(ImmutableList.of(recipe));
    when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

  }

  @Test
  public void testGetRecipes() {

    Assert.assertEquals(recipeService.getRecipes().size(), 1);
    Assert.assertEquals(recipeService.getRecipeById(1L), recipe);
    verify(recipeRepository, times(1)).findAll();
    verify(recipeRepository, times(1)).findById(1L);
  }
}