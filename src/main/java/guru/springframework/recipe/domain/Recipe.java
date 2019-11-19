package guru.springframework.recipe.domain;

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
import lombok.Data;


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

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
  private Set<Ingredient> ingredients = new HashSet<>();

  @Enumerated(EnumType.STRING)
  private Difficulity difficulity;

  @ManyToMany
  @JoinTable(
      name = "recipe_category",
      joinColumns = @JoinColumn(name = "recipe_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Set<Category> categories = new HashSet<>();


  public void setNotes(Notes notes) {
    this.notes = notes;
    notes.setRecipe(this);
  }

  public void addIngredient(Ingredient ingredient) {
    this.getIngredients().add(ingredient);
    ingredient.setRecipe(this);
  }

  public void addCategory(Category category) {
    this.getCategories().add(category);
    category.getRecipes().add(this);
  }
}
