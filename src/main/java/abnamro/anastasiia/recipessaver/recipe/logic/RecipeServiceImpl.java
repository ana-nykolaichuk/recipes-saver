package abnamro.anastasiia.recipessaver.recipe.logic;

import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeCreationRequest;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeService;
import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;

@Service
final class RecipeServiceImpl implements RecipeService {
  @Override
  public Recipe createRecipe(RecipeCreationRequest request) {
    throw new UnsupportedOperationException("Sit tight, it will be implemented soon.");
  }

  @Override
  public ImmutableSet<Recipe> getRecipes(ImmutableSet<String> ids) {
    throw new UnsupportedOperationException("Sit tight, it will be implemented soon.");
  }

  @Override
  public Recipe updateRecipe(String id, Recipe recipe) {
    throw new UnsupportedOperationException("Sit tight, it will be implemented soon.");
  }

  @Override
  public void deleteRecipe(String id) {
    throw new UnsupportedOperationException("Sit tight, it will be implemented soon.");
  }
}
