package guru.springframework.recipe.domain;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class CategoryTest {

  Category category;

  @Before
  public void setUp() {
    category = new Category();
  }

  @DataProvider
  public static Object[][] idProvider() {
    return new Object[][]{{4L}, {6L}};
  }

  @Test
  @UseDataProvider("idProvider")
  public void testGetId(Long id) {
    category.setId(id);

    Assert.assertEquals(category.getId(), id);
  }

  @Test
  public void testGetDescription() {
  }

  @Test
  public void testGetRecipes() {
  }
}