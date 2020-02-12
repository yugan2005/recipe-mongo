package guru.springframework.recipe.services;

import com.google.common.collect.ImmutableList;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.exceptions.NotFoundException;
import guru.springframework.recipe.repositories.RecipeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {


  private Recipe recipe;
  private Long recipeId;

  @Mock
  RecipeRepository recipeRepository;

  @InjectMocks
  private RecipeServiceImpl recipeService;

  @BeforeEach
  void setUp() {
    recipe = new Recipe();
    recipeId = 1L;
    lenient().when(recipeRepository.findAll()).thenReturn(ImmutableList.of(recipe));
    lenient().when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

  }

  @Test
  void testGetRecipes() {

    assertEquals(recipeService.getRecipes().size(), 1);
    verify(recipeRepository, times(1)).findAll();
  }

  @Test
  void testGetRecipeById() {

    assertEquals(recipeService.getRecipeById(recipeId), recipe);
    verify(recipeRepository, times(1)).findById(recipeId);
    verify(recipeRepository, never()).findAll();
  }

  @Test
  void testDeleteRecipeById() {
    recipeService.deleteRecipeById(recipeId);
    verify(recipeRepository, times(1)).deleteById(recipeId);
  }

  @Test
  void testIdNotFoundExceptionThrow() {
    when(recipeRepository.findById(2L)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> recipeService.getRecipeById(2L));
  }
}