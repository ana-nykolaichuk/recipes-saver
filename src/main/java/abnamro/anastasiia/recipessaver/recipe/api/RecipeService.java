package abnamro.anastasiia.recipessaver.recipe.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public interface RecipeService {
  /**
   * Creates a recipe based on the request and returns created entity.
   */
  Recipe createRecipe(RecipeCreationRequest request);

  /**
   * Returns all the recipe based on their IDs. If the recipe is not found, it won't be present in
   * the response.
   */
  ImmutableSet<Recipe> getRecipes(ImmutableSet<String> ids);

  /**
   * Updates the recipe with given ID. Throws an {@link IllegalArgumentException} if the original
   * recipe is not found.
   */
  Recipe updateRecipe(String id, Recipe recipe);

  /**
   * Deletes the recipe by ID. Does nothing, if the recipe with given ID doesn't exist.
   */
  void deleteRecipe(String id);
}
