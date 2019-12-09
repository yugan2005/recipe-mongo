package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.CategoryCommand;
import guru.springframework.recipe.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CategoryCommand2CategoryConverterTest {

  private static final Long ID = 1L;
  private static final String DESCRIPTION = "description";
  private CategoryCommand2CategoryConverter _categoryCommand2CategoryConverter =
      new CategoryCommand2CategoryConverter();

  @Test
  void convertNull() {
    assertNull(_categoryCommand2CategoryConverter.convert(null));
  }

  @Test
  void convertEmpty() {
    assertNotNull(_categoryCommand2CategoryConverter.convert(new CategoryCommand()));
  }

  @Test
  void convert() {
    CategoryCommand categoryCommand = new CategoryCommand();
    categoryCommand.setId(ID);
    categoryCommand.setDescription(DESCRIPTION);
    Category category = _categoryCommand2CategoryConverter.convert(categoryCommand);
    assertNotNull(category);
    assertEquals(ID, category.getId());
    assertEquals(DESCRIPTION, category.getDescription());
  }
}