package abnamro.anastasiia.recipessaver.recipe.api;

import com.google.common.collect.ImmutableSet;

public record Recipe(
        String id,
        String name,
        ImmutableSet<Ingredient> ingredients,
        String preparationInstructions,
        int servingsNumber,
        ImmutableSet<RecipeTag> tags) {}