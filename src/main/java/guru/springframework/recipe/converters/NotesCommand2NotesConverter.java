package guru.springframework.recipe.converters;

import guru.springframework.recipe.commandobjs.NotesCommand;
import guru.springframework.recipe.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class NotesCommand2NotesConverter implements Converter<NotesCommand, Notes> {

  @Synchronized
  @Nullable
  @Override
  public Notes convert(@Nullable NotesCommand source) {
    if (source == null) {
      return null;
    }

    final Notes notes = new Notes();
    notes.setRecipeNotes(source.getRecipeNotes());
    notes.setId(source.getId());
    return notes;
  }
}
