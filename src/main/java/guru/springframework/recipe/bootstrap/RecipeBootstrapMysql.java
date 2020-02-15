package guru.springframework.recipe.bootstrap;

import com.google.common.collect.Lists;
import guru.springframework.recipe.domain.Category;
import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.repositories.CategoryRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Profile({"dev", "prod"})
public class RecipeBootstrapMysql implements ApplicationListener<ContextRefreshedEvent> {
  private final CategoryRepository _categoryRepository;
  private final UnitOfMeasureRepository _unitOfMeasureRepository;

  public RecipeBootstrapMysql(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
    _categoryRepository = categoryRepository;
    _unitOfMeasureRepository = unitOfMeasureRepository;
  }

  @Override
  public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

    if (_categoryRepository.count() == 0) {
      loadCategories();
    }

    if (_unitOfMeasureRepository.count() == 0) {
      loadUnitOfMeasures();
    }
  }

  private void loadUnitOfMeasures() {

    UnitOfMeasure teaspoon = new UnitOfMeasure();
    teaspoon.setDescription("Teaspoon");

    UnitOfMeasure tablespoon = new UnitOfMeasure();
    tablespoon.setDescription("Tablespoon");

    UnitOfMeasure cup = new UnitOfMeasure();
    cup.setDescription("Cup");

    UnitOfMeasure pinch = new UnitOfMeasure();
    pinch.setDescription("Pinch");

    UnitOfMeasure ounce = new UnitOfMeasure();
    ounce.setDescription("Ounce");

    UnitOfMeasure dash = new UnitOfMeasure();
    dash.setDescription("Dash");

    UnitOfMeasure count = new UnitOfMeasure();
    count.setDescription("Count");

    _unitOfMeasureRepository.saveAll(Lists.newArrayList(teaspoon, tablespoon, cup, pinch, ounce, dash, count));
  }

  private void loadCategories() {
    Category american = new Category();
    american.setDescription("American");

    Category italian = new Category();
    italian.setDescription("Italian");

    Category mexican = new Category();
    mexican.setDescription("Mexican");

    Category chinese = new Category();
    chinese.setDescription("Chinese");

    Category fastfood = new Category();
    fastfood.setDescription("Fastfood");

    _categoryRepository.saveAll(Lists.newArrayList(american, italian, mexican, chinese, fastfood));
  }
}
