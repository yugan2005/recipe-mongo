package guru.springframework.recipe.bootstrap;

import com.google.common.collect.Lists;
import guru.springframework.recipe.domain.Category;
import guru.springframework.recipe.domain.Difficulity;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Notes;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.CategoryRepository;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final RecipeRepository _recipeRepository;
  private final UnitOfMeasureRepository _unitOfMeasureRepository;
  private final CategoryRepository _categoryRepository;

  public RecipeBootstrap(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
      CategoryRepository categoryRepository) {
    _recipeRepository = recipeRepository;
    _unitOfMeasureRepository = unitOfMeasureRepository;
    _categoryRepository = categoryRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    try {
      _recipeRepository.saveAll(createRecipes());
    } catch (Exception e) {
      e.printStackTrace();
    }

    log.debug("Loading Bootstrap Data");
  }

  private List<Recipe> createRecipes() throws Exception {

    // recipe for perfectGuacamole
    Recipe perfectGuacamole = new Recipe();
    perfectGuacamole.setDescription(
        "The BEST guacamole! EASY to make with ripe avocados, salt, serrano chiles, cilantro and lime. Garnish with red radishes or jicama. Serve with tortilla chips.");
    perfectGuacamole.setPrepTime(10);
    perfectGuacamole.setCookTime(45);
    perfectGuacamole.setServings(4);
    perfectGuacamole.setSource("simplyrecipes.com");
    perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
    perfectGuacamole.setDirections(
        "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n"
            + "\n"
            + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n"
            + "\n"
            + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n"
            + "\n"
            + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
            + "\n"
            + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
            + "\n"
            + "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n"
            + "\n"
            + "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n"
            + "\n");
    Notes perfectGuacamoleNote = new Notes();
    perfectGuacamoleNote.setRecipeNotes(
        "For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" + "\n"
            + "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole).\n"
            + "\n"
            + "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n"
            + "\n"
            + "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n"
            + "\n" + "For a deviled egg version with guacamole, try our Guacamole Deviled Eggs!");
    perfectGuacamole.setNotes(perfectGuacamoleNote);

    Ingredient avocado = new Ingredient("ripe avocado", BigDecimal.valueOf(2), null);

    Ingredient kosherSalt = new Ingredient("kosher salt", BigDecimal.valueOf(0.5),
        _unitOfMeasureRepository.findByDescription("Teaspoon")
        .orElseThrow(RecipeBootstrap::throwDataNotFoundException));

    perfectGuacamole.addIngredient(avocado);
    perfectGuacamole.addIngredient(kosherSalt);
    perfectGuacamole.setDifficulity(Difficulity.EASY);

    Category mexican = _categoryRepository.findByDescription("Mexican").orElseThrow(RecipeBootstrap::throwDataNotFoundException);
    Category american = _categoryRepository.findByDescription("American").orElseThrow(RecipeBootstrap::throwDataNotFoundException);
    Category chinese = _categoryRepository.findByDescription("Chinese").orElseThrow(RecipeBootstrap::throwDataNotFoundException);

    perfectGuacamole.addCategory(mexican);
    perfectGuacamole.addCategory(american);

    // recipe for Spicy Grilled Chicken Tacos
    Recipe grilledChicken = new Recipe();
    grilledChicken.setDescription(
        "Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
    grilledChicken.setCookTime(30);
    grilledChicken.setPrepTime(10);
    grilledChicken.setServings(5);
    grilledChicken.setSource("simplyrecipes.com");
    grilledChicken.setDifficulity(Difficulity.HARD);
    grilledChicken.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
    grilledChicken.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" + "\n"
        + "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n"
        + "\n" + "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" + "\n"
        + "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n"
        + "\n"
        + "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n"
        + "\n" + "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" + "\n"
        + "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n"
        + "\n");

    Notes grilledChickenNotes = new Notes();
    grilledChickenNotes.setRecipeNotes(
        "Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)\n"
            + "\n");
    grilledChicken.setNotes(grilledChickenNotes);

    Ingredient oregano = new Ingredient("dried oregano", BigDecimal.valueOf(1),
        _unitOfMeasureRepository.findByDescription("Teaspoon")
            .orElseThrow(RecipeBootstrap::throwDataNotFoundException));
    grilledChicken.addIngredient(oregano);

    Ingredient sugar = new Ingredient("sugar", BigDecimal.valueOf(1),
        _unitOfMeasureRepository.findByDescription("Tablespoon")
            .orElseThrow(RecipeBootstrap::throwDataNotFoundException));
    grilledChicken.addIngredient(sugar);

    grilledChicken.addCategory(chinese);
    grilledChicken.addCategory(american);

    return Lists.newArrayList(perfectGuacamole, grilledChicken);
  }

  private static Exception throwDataNotFoundException() {
    return new RuntimeException("No found in database");
  }
}
