package guru.springframework.recipe.domain;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;


@Data
@Entity
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;

  @Lob
  private String directions;

  @Lob
  private Byte[] image;

  @OneToOne(cascade = CascadeType.ALL)
  private Notes notes;

  @Enumerated(EnumType.STRING)
  private Difficulity difficulity;

  @Setter(AccessLevel.NONE)
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
  private Set<Ingredient> ingredients = new HashSet<>();

  @Setter(AccessLevel.NONE)
  @ManyToMany
  @JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  public void setNotes(Notes notes) {
    this.notes = notes;
    notes.setRecipe(this);
  }

  public Set<Ingredient> getIngredients() {
    return ImmutableSet.copyOf(ingredients);
  }

  public void addIngredient(Ingredient ingredient) {
    if (ingredient.getId() != null) {
      removeIngredientById(ingredient.getId());
    }
    ingredients.add(ingredient);
    ingredient.setRecipe(this);
  }

  public void removeIngredient(Ingredient ingredient) {
    ingredients.remove(ingredient);
  }

  public void removeIngredientById(@NotNull Long ingredientId) {
    ingredients.removeIf(ingredient -> ingredientId.equals(ingredient.getId()));
  }

  public Set<Category> getCategories() {
    return ImmutableSet.copyOf(categories);
  }

  public void addCategory(Category category) {
    removeCategoryById(category.getId());
    categories.add(category);
    category.addRecipe(this);
  }

  public void removeCategory(Category category) {
    categories.remove(category);
  }

  public void removeCategoryById(@NotNull Long categoryId) {
    categories.removeIf(category -> categoryId.equals(category.getId()));
  }
}
