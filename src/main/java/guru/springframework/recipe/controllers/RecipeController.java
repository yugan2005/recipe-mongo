package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.services.RecipeService;
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
public class RecipeController {

  private final RecipeService _recipeService;

  public RecipeController(RecipeService recipeService) {
    _recipeService = recipeService;
  }

  @RequestMapping("/{id}/show")
  public String showRecipe(@PathVariable String id, Model model) {

    log.debug("Getting Recipe Page");
    model.addAttribute("recipe", _recipeService.getRecipeById(Long.valueOf(id)));

    return "recipe/show";
  }

  // called after the form's submit button clicked
  @RequestMapping(value = "", method = RequestMethod.POST)
  public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
    RecipeCommand savedRecipeCommand = _recipeService.saveRecipeCommand(recipeCommand);

    // redirect which will ended up calling method showRecipe above
    return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
  }


  @RequestMapping("/new")
  public String newRecipe(Model model) {
    model.addAttribute("recipeCommand", new RecipeCommand());

    return "recipe/recipeForm";
  }
}
