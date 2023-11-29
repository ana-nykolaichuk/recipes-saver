package abnamro.anastasiia.recipessaver.recipe.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class RecipeUpdateRequest {
  private String recipeId;
  private RecipeDto recipe;
}
