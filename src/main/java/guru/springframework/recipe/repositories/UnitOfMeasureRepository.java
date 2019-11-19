package guru.springframework.recipe.repositories;

import guru.springframework.recipe.domain.UnitOfMeasure;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;


public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

  Optional<UnitOfMeasure> findByDescription(String description);

}
