package abnamro.anastasiia.recipessaver.recipe.api;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable {
  private String id;
  private String name;
  private Set<Ingredient> ingredients;
  private String preparationInstructions;
  private int servingsNumber;
  private Set<RecipeTag> recipeTags;
}
