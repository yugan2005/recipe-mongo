package guru.springframework.recipe.services;

import com.google.common.collect.ImmutableList;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

  private RecipeServiceImpl recipeService;
  private Recipe recipe;

  @Mock
  RecipeRepository recipeRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository);
    recipe = new Recipe();
    when(recipeRepository.findAll()).thenReturn(ImmutableList.of(recipe));
    when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

  }

  @Test
  void testGetRecipes() {

    Assert.assertEquals(recipeService.getRecipes().size(), 1);
    Assert.assertEquals(recipeService.getRecipeById(1L), recipe);
    verify(recipeRepository, times(1)).findAll();
    verify(recipeRepository, times(1)).findById(1L);
  }
}