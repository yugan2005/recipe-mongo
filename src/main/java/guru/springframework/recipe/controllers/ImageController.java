package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/recipe")
public class ImageController {

  private final RecipeService _recipeService;

  public ImageController(RecipeService recipeService) {
    _recipeService = recipeService;
  }

  @GetMapping("/{recipeId}/image")
  public String uploadImageForm(@PathVariable String recipeId, Model model) {
    RecipeCommand recipeCommand = _recipeService.getRecipeCommandById(Long.valueOf(recipeId));
    model.addAttribute("recipeCommand", recipeCommand);
    return "/recipe/imageuploadForm";
  }

  @PostMapping("/{recipeId}/image")
  public String uploadImage(@PathVariable String recipeId, @RequestParam("imageFile") MultipartFile multipartFile)
      throws IOException {
    Recipe recipe = _recipeService.getRecipeById(Long.valueOf(recipeId));
    recipe.setImage(ArrayUtils.toObject(multipartFile.getBytes()));
    _recipeService.saveRecipe(recipe);
    return "redirect:/recipe/" + recipeId + "/show";
  }

  @GetMapping("/{recipeId}/image/show")
  public void renderImageFromRecipe(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
    Recipe recipe = _recipeService.getRecipeById(Long.valueOf(recipeId));

    if (ArrayUtils.isEmpty(recipe.getImage())) {
      return;
    }

    response.setContentType(MediaType.IMAGE_JPEG_VALUE);

    IOUtils.copy(new ByteArrayInputStream(ArrayUtils.toPrimitive(recipe.getImage())), response.getOutputStream());
  }
}
