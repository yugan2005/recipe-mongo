package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class UnitOfMeasure2UnitOfMeasureCommandConverter implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

  @Synchronized
  @Nullable
  @Override
  public UnitOfMeasureCommand convert(@Nullable UnitOfMeasure source) {

    if (source == null) {
      return null;
    }

    final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setDescription(source.getDescription());
    unitOfMeasureCommand.setId(source.getId());
    return unitOfMeasureCommand;
  }
}
