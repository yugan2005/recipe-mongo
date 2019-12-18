package guru.springframework.recipe.domain;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;


@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  @Setter(AccessLevel.NONE)
  @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
  private Set<Recipe> recipes = new HashSet<>();

  public Set<Recipe> getRecipes() {
    return ImmutableSet.copyOf(recipes);
  }

  public void addRecipe(Recipe recipe) {
    if (recipe.getId() != null) {
      removeRecipeById(recipe.getId());
    }
    recipes.add(recipe);
  }

  public void removeRecipe(Recipe recipe) {
    recipes.remove(recipe);
  }

  public void removeRecipeById(@NotNull Long recipeId) {
    recipes.removeIf(recipe -> recipeId.equals(recipe.getId()));
  }
}
