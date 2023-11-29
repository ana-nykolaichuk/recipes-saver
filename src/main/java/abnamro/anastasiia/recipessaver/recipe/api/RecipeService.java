package abnamro.anastasiia.recipessaver.recipe.api;

import com.google.common.collect.ImmutableSet;

public interface RecipeService {
  /**
   * Creates a recipe based on the recipe DTO and returns created entity.
   */
  Recipe createRecipe(RecipeDto recipe);

  /**
   * Returns all the recipe based on their IDs. If the recipe is not found, it won't be present in
   * the response.
   */
  ImmutableSet<Recipe> getRecipes(ImmutableSet<String> ids);

  /**
   * Updates the recipe with given ID. Throws an {@link IllegalArgumentException} if the original
   * recipe is not found.
   */
  Recipe updateRecipe(String id, RecipeDto recipe);

  /**
   * Deletes the recipe by ID. Does nothing, if the recipe with given ID doesn't exist.
   */
  void deleteRecipe(String id);
}
