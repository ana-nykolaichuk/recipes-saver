package abnamro.anastasiia.recipessaver.recipe.logic.data;

import abnamro.anastasiia.recipessaver.recipe.api.Ingredient;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeTag;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document("recipe")
public class RecipeDocument {
  @Id
  private String id;
  private String name;
  private Set<Ingredient> ingredients;
  private String preparationInstructions;
  private int servingsNumber;
  private Set<RecipeTag> recipeTags;
}
