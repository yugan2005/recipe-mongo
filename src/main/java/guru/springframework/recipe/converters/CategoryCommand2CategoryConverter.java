package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.CategoryCommand;
import guru.springframework.recipe.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class CategoryCommand2CategoryConverter implements Converter<CategoryCommand, Category> {

  @Synchronized
  @Nullable
  @Override
  public Category convert(@Nullable CategoryCommand source) {
    if (source == null) {
      return null;
    }

    final Category category = new Category();
    category.setId(source.getId());
    category.setDescription(source.getDescription());

    return category;
  }
}
