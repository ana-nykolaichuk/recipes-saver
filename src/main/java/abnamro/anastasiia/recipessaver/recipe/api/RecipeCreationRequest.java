package abnamro.anastasiia.recipessaver.recipe.api;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableSet;

public record RecipeCreationRequest(
        String name,
        ImmutableSet<Ingredient> ingredients,
        String preparationInstructions,
        int servingsNumber,
        ImmutableSet<RecipeTag> tags) {
    public RecipeCreationRequest {
        checkArgument(!name.isBlank(), "The name should be present.");
        checkArgument(!ingredients.isEmpty(), "At least one ingredient must be present.");
        checkArgument(servingsNumber > 0, "The servings number should be more than 0.");
    }
}
