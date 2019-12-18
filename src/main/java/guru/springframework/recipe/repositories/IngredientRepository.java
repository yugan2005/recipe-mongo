package guru.springframework.recipe.repositories;

import guru.springframework.recipe.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;


public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
