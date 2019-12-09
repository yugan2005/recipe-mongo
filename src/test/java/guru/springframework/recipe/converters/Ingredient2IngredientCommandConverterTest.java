package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.UnitOfMeasure;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Ingredient2IngredientCommandConverterTest {
  private final UnitOfMeasure2UnitOfMeasureCommandConverter _unitOfMeasure2UnitOfMeasureCommandConverter =
      new UnitOfMeasure2UnitOfMeasureCommandConverter();
  private final Ingredient2IngredientCommandConverter _ingredient2IngredientCommandConverter =
      new Ingredient2IngredientCommandConverter(_unitOfMeasure2UnitOfMeasureCommandConverter);

  private static final Long INGREDIENT_ID = 1L;
  private static final Long UNIT_OF_MEASURE_ID = 2L;
  private static final String INGREDIENT_DESCRIPTION = "ingredient";
  private static final String UNIT_OF_MEASURE_DESCRIPTION = "uom";
  private static final BigDecimal AMOUNT = BigDecimal.valueOf(2.0);

  private UnitOfMeasure unitOfMeasure;
  private Ingredient ingredient;


  @BeforeEach
  void setUp() {
    unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setId(UNIT_OF_MEASURE_ID);
    unitOfMeasure.setDescription(UNIT_OF_MEASURE_DESCRIPTION);
    ingredient = new Ingredient();
    ingredient.setId(INGREDIENT_ID);
    ingredient.setDescription(INGREDIENT_DESCRIPTION);
    ingredient.setAmount(AMOUNT);
  }

  @Test
  void convertNull() {
    assertNull(_ingredient2IngredientCommandConverter.convert(null));
  }

  @Test
  void convertEmpty() {
    assertNotNull(_ingredient2IngredientCommandConverter.convert(new Ingredient()));
  }

  @Test
  void convertNullUnitOfMeasure() {
    assertNotNull(_ingredient2IngredientCommandConverter.convert(ingredient));
  }

  @Test
  void convert(){
    ingredient.setUnitOfMeasure(unitOfMeasure);
    IngredientCommand ingredientCommand = _ingredient2IngredientCommandConverter.convert(ingredient);
    assertNotNull(ingredientCommand);
    assertEquals(INGREDIENT_DESCRIPTION, ingredientCommand.getDescription());
    assertEquals(INGREDIENT_ID, ingredientCommand.getId());
    assertEquals(AMOUNT, ingredientCommand.getAmount());
    assertEquals(UNIT_OF_MEASURE_DESCRIPTION, ingredientCommand.getUnitOfMeasure().getDescription());
    assertEquals(UNIT_OF_MEASURE_ID, ingredientCommand.getUnitOfMeasure().getId());
  }
}