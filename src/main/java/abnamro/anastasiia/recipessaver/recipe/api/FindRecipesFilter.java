package abnamro.anastasiia.recipessaver.recipe.api;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRecipesFilter {
  @Builder.Default
  private Optional<String> name = Optional.empty();
  @Builder.Default
  private Set<RecipeTag> tagsIncluded = ImmutableSet.of();
  @Builder.Default
  private Set<RecipeTag> tagsExcluded = ImmutableSet.of();
  @Builder.Default
  private Optional<Integer> numberOfServings = Optional.empty();
  @Builder.Default
  private Set<String> ingredientsIncluded = ImmutableSet.of();
  @Builder.Default
  private Set<String> ingredientsExcluded = ImmutableSet.of();
  @Builder.Default
  private Optional<String> preparationInstructions = Optional.empty();
}
