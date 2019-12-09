package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.CategoryCommand;
import guru.springframework.recipe.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class Category2CategoryCommandConverter implements Converter<Category, CategoryCommand> {

  @Synchronized
  @Nullable
  @Override
  public CategoryCommand convert(@Nullable Category source) {
    if (source == null) {
      return null;
    }

    final CategoryCommand categoryCommand = new CategoryCommand();
    categoryCommand.setDescription(source.getDescription());
    categoryCommand.setId(source.getId());

    return categoryCommand;
  }
}
