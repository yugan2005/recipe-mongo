package guru.springframework.recipe.services;

import com.google.common.collect.ImmutableSet;
import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.converters.Recipe2RecipeCommandConverter;
import guru.springframework.recipe.converters.RecipeCommand2RecipeConverter;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository _recipeRepository;
  private final RecipeCommand2RecipeConverter _recipeCommand2RecipeConverter;
  private final Recipe2RecipeCommandConverter _recipe2RecipeCommandConverter;

  public RecipeServiceImpl(RecipeRepository recipeRepository,
      RecipeCommand2RecipeConverter recipeCommand2RecipeConverter,
      Recipe2RecipeCommandConverter recipe2RecipeCommandConverter) {
    _recipeRepository = recipeRepository;
    _recipeCommand2RecipeConverter = recipeCommand2RecipeConverter;
    _recipe2RecipeCommandConverter = recipe2RecipeCommandConverter;
  }

  @Override
  public Set<Recipe> getRecipes() {
    return ImmutableSet.copyOf(_recipeRepository.findAll());
  }

  @Override
  public Recipe getRecipeById(Long id) {
    return _recipeRepository.findById(id).orElse(null);
  }

  @Transactional
  @Override
  public RecipeCommand saveRecipeCommand(RecipeCommand detachedRecipeCommand) {
    Recipe detachedRecipe = _recipeCommand2RecipeConverter.convert(detachedRecipeCommand);

    if (detachedRecipe == null) {
      return null;
    }

    Recipe savedRecipe = _recipeRepository.save(detachedRecipe);
    log.debug("saved recipeId: " + savedRecipe.getId());
    return _recipe2RecipeCommandConverter.convert(savedRecipe);
  }
}
