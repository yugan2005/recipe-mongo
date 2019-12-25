package guru.springframework.recipe.controllers;

import guru.springframework.recipe.commandobjs.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

  @Mock
  RecipeService _recipeService;

  @InjectMocks
  ImageController _imageController;

  private MockMvc _mockMvc;

  private Long _recipeId;
  private Recipe _recipe;
  private String _mockImage;
  private Byte[] _image;

  @BeforeEach
  void setUp() {
    _mockMvc = MockMvcBuilders.standaloneSetup(_imageController).build();

    _recipeId = 2L;
    _recipe = new Recipe();
    _recipe.setId(_recipeId);
    _mockImage = "fake image";
    _image = ArrayUtils.toObject(_mockImage.getBytes());
  }

  @Test
  void uploadImageForm() throws Exception {
    RecipeCommand recipeCommand = new RecipeCommand();
    recipeCommand.setId(_recipeId);

    when(_recipeService.getRecipeCommandById(_recipeId)).thenReturn(recipeCommand);

    _mockMvc.perform(get("/recipe/{recipeId}/image", _recipeId)).
        andExpect(status().isOk()).
        andExpect(view().name("/recipe/imageuploadForm")).
        andExpect(model().attributeExists("recipeCommand")).
        andExpect(model().attribute("recipeCommand", hasProperty("id", equalTo(_recipeId))));
  }

  @Test
  void uploadImage() throws Exception {

    when(_recipeService.getRecipeById(_recipeId)).thenReturn(_recipe);

    ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

    MockMultipartFile mockMultipartFile =
        new MockMultipartFile("imageFile", null, MediaType.MULTIPART_FORM_DATA_VALUE, _mockImage.getBytes());

    _mockMvc.perform(multipart("/recipe/{recipeId}/image", _recipeId).
        file(mockMultipartFile)).
        andExpect(status().is3xxRedirection()).
        andExpect(view().name("redirect:/recipe/" + _recipeId + "/show"));

    verify(_recipeService).getRecipeById(_recipeId);
    verify(_recipeService).saveRecipe(recipeArgumentCaptor.capture());

    assertArrayEquals(ArrayUtils.toPrimitive(recipeArgumentCaptor.getValue().getImage()), _mockImage.getBytes());
  }

  @Test
  void renderImageFromRecipe() throws Exception {
    _recipe.setImage(_image);

    when(_recipeService.getRecipeById(_recipeId)).thenReturn(_recipe);

    MockHttpServletResponse response = _mockMvc.perform(get("/recipe/{recipeId}/image/show", _recipeId)).
        andExpect(status().isOk()).
        andReturn().getResponse();

    verify(_recipeService, times(1)).getRecipeById(_recipeId);

    assertEquals(response.getContentType(), MediaType.IMAGE_JPEG_VALUE);
    assertArrayEquals(response.getContentAsByteArray(), _mockImage.getBytes());
  }

  @Test
  void renderImageFromRecipeWithoutImage() throws Exception {
    when(_recipeService.getRecipeById(_recipeId)).thenReturn(_recipe);

    MockHttpServletResponse response = _mockMvc.perform(get("/recipe/{recipeId}/image/show", _recipeId)).
        andExpect(status().isOk()).
        andReturn().getResponse();

    verify(_recipeService, times(1)).getRecipeById(_recipeId);

    assertTrue(ArrayUtils.isEmpty(response.getContentAsByteArray()));
  }
}