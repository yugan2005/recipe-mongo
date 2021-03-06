package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.commandobjs.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.IngredientService;
import guru.springframework.recipe.services.RecipeService;
import guru.springframework.recipe.services.UnitOfMeasureService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientsController {

  private final RecipeService _recipeService;
  private final IngredientService _ingredientService;
  private final UnitOfMeasureService _unitOfMeasureService;

  public IngredientsController(RecipeService recipeService, IngredientService ingredientService,
      UnitOfMeasureService unitOfMeasureService) {
    _recipeService = recipeService;
    _ingredientService = ingredientService;
    _unitOfMeasureService = unitOfMeasureService;
  }

  @RequestMapping("/{recipeId}/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model) {
    log.debug("calling ingredients for recipeId: " + recipeId);

    model.addAttribute("recipe", _recipeService.getRecipeCommandById(Long.valueOf(recipeId)));
    return "/recipe/ingredient/list";
  }

  @RequestMapping("/ingredient/{ingredientId}/show")
  public String showIngredient(@PathVariable String ingredientId, Model model) {
    IngredientCommand ingredientCommand = _ingredientService.findIngredientCommandById(Long.valueOf(ingredientId));
    model.addAttribute("ingredientCommand", ingredientCommand);
    return "/recipe/ingredient/show";
  }

  @RequestMapping("/ingredient/{ingredientId}/update")
  public String updateIngredient(@PathVariable String ingredientId, Model model) {
    IngredientCommand ingredientCommand = _ingredientService.findIngredientCommandById(Long.valueOf(ingredientId));
    model.addAttribute("ingredientCommand", ingredientCommand);

    List<UnitOfMeasureCommand> unitOfMeasureCommands = _unitOfMeasureService.getUnitOfMeasureCommands();
    model.addAttribute("unitOfMeasureCommands", unitOfMeasureCommands);
    return "/recipe/ingredient/ingredientForm";
  }

  @RequestMapping("/{recipeId}/ingredient/new")
  public String createIngredient(@PathVariable String recipeId, Model model) {
    // Note that we are going to use the same ingredientForm, we need provide Ids for the hidden text
    // Also, in ingredientForm we are calling ingredientCommand.unitOfMeasure.id

    IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(Long.valueOf(recipeId));
    ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

    List<UnitOfMeasureCommand> unitOfMeasureCommands = _unitOfMeasureService.getUnitOfMeasureCommands();

    model.addAttribute("ingredientCommand", ingredientCommand);
    model.addAttribute("unitOfMeasureCommands", unitOfMeasureCommands);

    return "/recipe/ingredient/ingredientForm";
  }

  @RequestMapping(value = "/ingredient", method = RequestMethod.POST)
  public String createOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
    IngredientCommand savedIngredientCommand = _ingredientService.saveIngredientCommand(ingredientCommand);
    return "redirect:/recipe/ingredient/" + savedIngredientCommand.getId() + "/show";
  }

  @RequestMapping("/ingredient/{ingredientId}/delete")
  public String delete(@PathVariable String ingredientId) {
    Ingredient ingredient = _ingredientService.findIngredientById(Long.valueOf(ingredientId));
    Recipe recipe = ingredient.getRecipe();
    _ingredientService.removeIngredient(ingredient);

    return "redirect:/recipe/" + recipe.getId() + "/ingredients";
  }
}