package abnamro.anastasiia.recipessaver.recipe.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
  private String name;
  private String amountDescription;
}
