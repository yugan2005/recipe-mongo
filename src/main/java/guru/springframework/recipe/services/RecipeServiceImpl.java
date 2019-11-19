package guru.springframework.recipe.services;

import com.google.common.collect.ImmutableSet;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository _recipeRepository;

  public RecipeServiceImpl(RecipeRepository recipeRepository) {
    _recipeRepository = recipeRepository;
  }

  @Override
  public Set<Recipe> getRecipes() {
    return ImmutableSet.copyOf(_recipeRepository.findAll());
  }

  @Override
  public Recipe getRecipeById(Long id) {
    return _recipeRepository.findById(id).orElse(null);
  }
}
