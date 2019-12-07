package guru.springframework.recipe.repositories;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

  @Autowired
  UnitOfMeasureRepository unitOfMeasureRepository;

  @Test
  void testFindByDescriptionTeaspoon() {
    Assert.assertTrue(unitOfMeasureRepository.findByDescription("Teaspoon").isPresent());
    Assert.assertEquals(unitOfMeasureRepository.findByDescription("Teaspoon").get().getDescription(), "Teaspoon");
  }

  @Test
  void testFindByDescriptionCup() {
    Assert.assertTrue(unitOfMeasureRepository.findByDescription("Cup").isPresent());
    Assert.assertEquals(unitOfMeasureRepository.findByDescription("Cup").get().getDescription(), "Cup");
  }
}