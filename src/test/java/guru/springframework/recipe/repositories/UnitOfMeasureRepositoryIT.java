package guru.springframework.recipe.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

  @Autowired
  UnitOfMeasureRepository unitOfMeasureRepository;

  @Test
  void testFindByDescriptionTeaspoon() {
    assertTrue(unitOfMeasureRepository.findByDescription("Teaspoon").isPresent());
    assertEquals(unitOfMeasureRepository.findByDescription("Teaspoon").get().getDescription(), "Teaspoon");
  }

  @Test
  void testFindByDescriptionCup() {
    assertTrue(unitOfMeasureRepository.findByDescription("Cup").isPresent());
    assertEquals(unitOfMeasureRepository.findByDescription("Cup").get().getDescription(), "Cup");
  }
}