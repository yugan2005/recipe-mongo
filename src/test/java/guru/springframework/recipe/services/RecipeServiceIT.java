package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.converters.Recipe2RecipeCommandConverter;
import guru.springframework.recipe.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceIT {

  @Autowired
  private RecipeService _recipeService;

  @Autowired
  private Recipe2RecipeCommandConverter _recipe2RecipeCommandConverter;

  private static final String DESCRIPTION = "new description";

  @BeforeEach
  void setUp() {
  }

  @Transactional
  @Test
  void updateRecipe() {
    Recipe recipe = _recipeService.getRecipes().iterator().next();
    long id = recipe.getId();
    RecipeCommand detachedRecipeCommand = _recipe2RecipeCommandConverter.convert(recipe);

    assertNotNull(detachedRecipeCommand);
    detachedRecipeCommand.setDescription(DESCRIPTION);
    RecipeCommand savedRecipeCommand = _recipeService.saveRecipeCommand(detachedRecipeCommand);

    Recipe savedRecipe = _recipeService.getRecipeById(id);

    assertEquals(savedRecipeCommand.getId(), savedRecipe.getId());
    assertEquals(DESCRIPTION, savedRecipe.getDescription());
  }
}