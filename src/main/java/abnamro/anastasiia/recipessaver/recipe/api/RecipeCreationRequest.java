package abnamro.anastasiia.recipessaver.recipe.api;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreationRequest {
  private String name;
  private Set<Ingredient> ingredients;
  private String preparationInstructions;
  private int servingsNumber;
  private Set<RecipeTag> recipeTags;

  public RecipeCreationRequest name(String name) {
    checkArgument(!name.isBlank(), "The name should be present.");
    this.name = name;
    return this;
  }

  public RecipeCreationRequest ingredients(Set<Ingredient> ingredients) {
    checkArgument(!ingredients.isEmpty(), "At least one ingredient must be present.");
    this.ingredients = ingredients;
    return this;
  }

  public RecipeCreationRequest servingsNumber(int servingsNumber) {
    checkArgument(servingsNumber > 0, "The servings number should be more than 0.");
    this.servingsNumber = servingsNumber;
    return this;
  }
}
