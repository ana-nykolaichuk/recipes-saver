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
  private Amount amount;

  @Data
  @Builder
  public static class Amount {
    double amount;
    IngredientAmountType type;
  }

  public enum IngredientAmountType {
    GR,
    KG,
    ST,
    TBSP,
    TSP
  }
}
