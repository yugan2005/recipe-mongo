package guru.springframework.recipe.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

  @Autowired
  UnitOfMeasureRepository unitOfMeasureRepository;

  @Test
  public void testFindByDescriptionTeaspoon() {
    Assert.assertTrue(unitOfMeasureRepository.findByDescription("Teaspoon").isPresent());
    Assert.assertEquals(unitOfMeasureRepository.findByDescription("Teaspoon").get().getDescription(), "Teaspoon");
  }

  @Test
  public void testFindByDescriptionCup() {
    Assert.assertTrue(unitOfMeasureRepository.findByDescription("Cup").isPresent());
    Assert.assertEquals(unitOfMeasureRepository.findByDescription("Cup").get().getDescription(), "Cup");
  }
}