package guru.springframework.recipe.services;

import guru.springframework.recipe.commandobjs.UnitOfMeasureCommand;
import guru.springframework.recipe.converters.UnitOfMeasure2UnitOfMeasureCommandConverter;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;


@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
  private final UnitOfMeasureRepository _unitOfMeasureRepository;
  private final UnitOfMeasure2UnitOfMeasureCommandConverter _unitOfMeasure2UnitOfMeasureCommandConverter;

  public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
      UnitOfMeasure2UnitOfMeasureCommandConverter unitOfMeasure2UnitOfMeasureCommandConverter) {
    _unitOfMeasureRepository = unitOfMeasureRepository;
    _unitOfMeasure2UnitOfMeasureCommandConverter = unitOfMeasure2UnitOfMeasureCommandConverter;
  }

  @Override
  public List<UnitOfMeasureCommand> getUnitOfMeasureCommands() {
    return StreamSupport.stream(_unitOfMeasureRepository.findAll().spliterator(), false).
        map(_unitOfMeasure2UnitOfMeasureCommandConverter::convert).
        filter(Objects::nonNull).
        sorted(Comparator.comparingInt(uom -> uom.getId().intValue())).
        collect(Collectors.toList());
  }
}
