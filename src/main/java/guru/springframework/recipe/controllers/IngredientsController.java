package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.IngredientCommand;
import guru.springframework.recipe.services.IngredientService;
import guru.springframework.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientsController {

  private final RecipeService _recipeService;
  private final IngredientService _ingredientService;

  public IngredientsController(RecipeService recipeService, IngredientService ingredientService) {
    _recipeService = recipeService;
    _ingredientService = ingredientService;
  }

  @RequestMapping("/{recipeId}/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model) {
    log.debug("calling ingredients for recipeId: " + recipeId);

    model.addAttribute("recipe", _recipeService.getRecipeCommandById(Long.valueOf(recipeId)));
    return "/recipe/ingredient/list";
  }

  @RequestMapping("/{recipeId}/ingredient/{ingredientId}/show")
  public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
    IngredientCommand ingredientCommand =
        _ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
    model.addAttribute("ingredientCommand", ingredientCommand);
    return "/recipe/ingredient/show";
  }
}