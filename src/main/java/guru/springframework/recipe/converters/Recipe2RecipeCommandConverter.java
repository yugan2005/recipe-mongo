package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import java.util.stream.Collectors;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class Recipe2RecipeCommandConverter implements Converter<Recipe, RecipeCommand> {

  private final Category2CategoryCommandConverter _category2CategoryCommandConverter;
  private final Ingredient2IngredientCommandConverter _ingredient2IngredientCommandConverter;
  private final Notes2NotesCommandConverter _notes2NotesCommandConverter;

  public Recipe2RecipeCommandConverter(Category2CategoryCommandConverter category2CategoryCommandConverter,
      Ingredient2IngredientCommandConverter ingredient2IngredientCommandConverter,
      Notes2NotesCommandConverter notes2NotesCommandConverter) {
    _category2CategoryCommandConverter = category2CategoryCommandConverter;
    _ingredient2IngredientCommandConverter = ingredient2IngredientCommandConverter;
    _notes2NotesCommandConverter = notes2NotesCommandConverter;
  }

  @SuppressWarnings("Duplicates")
  @Synchronized
  @Nullable
  @Override
  public RecipeCommand convert(@Nullable Recipe source) {
    if (source == null) {
      return null;
    }

    final RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(source.getId());
    recipeCommand.setCookTime(source.getCookTime());
    recipeCommand.setDifficulity(source.getDifficulity());
    recipeCommand.setDescription(source.getDescription());
    recipeCommand.setDirections(source.getDirections());
    recipeCommand.setPrepTime(source.getPrepTime());
    recipeCommand.setServings(source.getServings());
    recipeCommand.setSource(source.getSource());
    recipeCommand.setUrl(source.getUrl());
    recipeCommand.setCategories(source.getCategories().stream().
        map(_category2CategoryCommandConverter::convert).collect(Collectors.toSet()));
    recipeCommand.setIngredients(source.getIngredients().stream().
        map(_ingredient2IngredientCommandConverter::convert).collect(Collectors.toSet()));
    recipeCommand.setNotes(_notes2NotesCommandConverter.convert(source.getNotes()));
    return recipeCommand;
  }
}
