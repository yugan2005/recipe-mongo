package guru.springframework.recipe.repositories;

import guru.springframework.recipe.domain.Category;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;


public interface CategoryRepository extends CrudRepository<Category, Long> {

  Optional<Category> findByDescription(String description);

}
