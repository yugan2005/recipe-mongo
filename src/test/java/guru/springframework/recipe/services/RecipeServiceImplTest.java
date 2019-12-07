package guru.springframework.recipe.services;

import com.google.common.collect.ImmutableList;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {


  private Recipe recipe;

  @Mock
  RecipeRepository recipeRepository;

  @InjectMocks
  private RecipeServiceImpl recipeService;

  @BeforeEach
  void setUp() {
    recipe = new Recipe();
    lenient().when(recipeRepository.findAll()).thenReturn(ImmutableList.of(recipe));
    lenient().when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

  }

  @Test
  void testGetRecipes() {

    Assert.assertEquals(recipeService.getRecipes().size(), 1);
    verify(recipeRepository, times(1)).findAll();
  }

  @Test
  void testGetRecipeById() {

    Assert.assertEquals(recipeService.getRecipeById(1L), recipe);
    verify(recipeRepository, times(1)).findById(1L);
    verify(recipeRepository, never()).findAll();
  }
}