package abnamro.anastasiia.recipessaver.recipe.api;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@Builder
@Data
@With
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
