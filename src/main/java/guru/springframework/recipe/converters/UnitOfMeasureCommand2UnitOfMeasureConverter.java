package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class UnitOfMeasureCommand2UnitOfMeasureConverter implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

  @Nullable
  @Synchronized
  @Override
  public UnitOfMeasure convert(@Nullable UnitOfMeasureCommand source) {
    if (source == null) {
      return null;
    }

    final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
    unitOfMeasure.setDescription(source.getDescription());
    unitOfMeasure.setId(source.getId());

    return unitOfMeasure;
  }
}
