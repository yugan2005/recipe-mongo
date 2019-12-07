package guru.springframework.recipe.domain;

import com.tngtech.junit.dataprovider.DataProvider;
import com.tngtech.junit.dataprovider.UseDataProvider;
import com.tngtech.junit.dataprovider.UseDataProviderExtension;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(UseDataProviderExtension.class)
public class CategoryTest {

  private Category category;

  @BeforeEach
  void setUp() {
    category = new Category();
  }

  @DataProvider
  public static Object[][] idProvider() {
    return new Object[][]{{4L}, {6L}};
  }

  @TestTemplate
  @UseDataProvider("idProvider")
  void testGetId(Long id) {

    category.setId(id);

    Assert.assertEquals(category.getId(), id);
  }

  @Test
  void testGetDescription() {
  }

  @Test
  void testGetRecipes() {
  }
}