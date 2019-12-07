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
public class RecipeController {

  private final RecipeService _recipeService;

  public RecipeController(RecipeService recipeService) {
    _recipeService = recipeService;
  }

  @RequestMapping("/show/{id}")
  public String showRecipe(@PathVariable String id, Model model) {

    log.debug("Getting Recipe Page");
    model.addAttribute("recipe", _recipeService.getRecipeById(Long.valueOf(id)));

    return "recipe/show";
  }

}
