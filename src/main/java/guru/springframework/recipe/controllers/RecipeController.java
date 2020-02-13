package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.services.RecipeService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

  private static final String RECIPE_FORM = "recipe/recipeForm";
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
  public String saveOrUpdate(@Valid @ModelAttribute RecipeCommand recipeCommand, BindingResult result) {

    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> log.info(error.toString()));
      return RECIPE_FORM;
    }

    RecipeCommand savedRecipeCommand = _recipeService.saveRecipeCommand(recipeCommand);

    // redirect which will ended up calling method showRecipe above
    return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
  }


  @RequestMapping("/new")
  public String newRecipe(Model model) {
    model.addAttribute("recipeCommand", new RecipeCommand());

    return RECIPE_FORM;
  }

  @RequestMapping("/{id}/update")
  public String update(@PathVariable String id, Model model) {
    RecipeCommand recipeCommand = _recipeService.getRecipeCommandById(Long.valueOf(id));
    model.addAttribute("recipeCommand", recipeCommand);
    return "recipe/recipeForm";
  }

  @RequestMapping("/{id}/delete")
  public String delete(@PathVariable String id) {
    _recipeService.deleteRecipeById(Long.valueOf(id));
    return "redirect:/";
  }

}
