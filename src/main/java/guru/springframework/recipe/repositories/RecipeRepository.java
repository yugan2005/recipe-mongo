package guru.springframework.recipe.repositories;

import guru.springframework.recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;


public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
