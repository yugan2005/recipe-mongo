package guru.springframework.recipe.controllers;

import guru.springframework.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

  private final RecipeService _recipeService;

  public IndexController(RecipeService recipeService) {
    _recipeService = recipeService;
  }

  @RequestMapping({"", "/", "/index", "/index.html"})
  public String index(Model model) {

    log.debug("Getting Index Page");
    model.addAttribute("recipes", _recipeService.getRecipes());

    return "index";
  }

}
