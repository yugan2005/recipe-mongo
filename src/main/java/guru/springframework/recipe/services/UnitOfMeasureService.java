package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.UnitOfMeasureCommand;
import java.util.List;


public interface UnitOfMeasureService {
  List<UnitOfMeasureCommand> getUnitOfMeasureCommands();
}
