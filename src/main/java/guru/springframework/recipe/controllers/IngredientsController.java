package guru.springframework.recipe.controllers;

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

  public IngredientsController(RecipeService recipeService) {
    _recipeService = recipeService;
  }

  @RequestMapping("/{recipeId}/ingredients")
  public String listIngredients(@PathVariable String recipeId, Model model) {
    log.debug("calling ingredients for recipeId: " + recipeId);

    model.addAttribute("recipe", _recipeService.getRecipeCommandById(Long.valueOf(recipeId)));
    return "/recipe/ingredient/list";
  }
}
