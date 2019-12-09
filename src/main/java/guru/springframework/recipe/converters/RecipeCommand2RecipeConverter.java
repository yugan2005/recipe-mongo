package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import java.util.stream.Collectors;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class RecipeCommand2RecipeConverter implements Converter<RecipeCommand, Recipe> {
  private final NotesCommand2NotesConverter notesCommand2NotesConverter;
  private final IngredientCommand2IngredientConverter ingredientCommand2IngredientConverter;
  private final CategoryCommand2CategoryConverter categoryCommand2CategoryConverter;

  public RecipeCommand2RecipeConverter(NotesCommand2NotesConverter notesCommand2NotesConverter,
      IngredientCommand2IngredientConverter ingredientCommand2IngredientConverter,
      CategoryCommand2CategoryConverter categoryCommand2CategoryConverter) {
    this.notesCommand2NotesConverter = notesCommand2NotesConverter;
    this.ingredientCommand2IngredientConverter = ingredientCommand2IngredientConverter;
    this.categoryCommand2CategoryConverter = categoryCommand2CategoryConverter;
  }

  @SuppressWarnings("Duplicates")
  @Synchronized
  @Nullable
  @Override
  public Recipe convert(@Nullable RecipeCommand source) {
    if (source == null) {
      return null;
    }

    final Recipe recipe = new Recipe();
    recipe.setId(source.getId());
    recipe.setDifficulity(source.getDifficulity());
    recipe.setNotes(notesCommand2NotesConverter.convert(source.getNotes()));
    recipe.setCookTime(source.getCookTime());
    recipe.setPrepTime(source.getPrepTime());
    recipe.setServings(source.getServings());
    recipe.setDescription(source.getDescription());
    //noinspection Duplicates
    recipe.setCategories(source.getCategories().stream().
        map(categoryCommand2CategoryConverter::convert).collect(Collectors.toSet()));
    //noinspection Duplicates
    recipe.setIngredients(source.getIngredients().stream().
        map(ingredientCommand2IngredientConverter::convert).collect(Collectors.toSet()));
    recipe.setDirections(source.getDirections());
    recipe.setUrl(source.getUrl());
    recipe.setSource(source.getSource());
    return recipe;
  }
}
