package abnamro.anastasiia.recipessaver.recipe.logic;

import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeService;
import abnamro.anastasiia.recipessaver.recipe.logic.data.RecipeRepository;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
final class RecipeServiceImpl implements RecipeService {
  private static final Logger LOG = LoggerFactory.getLogger(RecipeServiceImpl.class);
  private final RecipeRepository repository;

  RecipeServiceImpl(RecipeRepository repository) {
    this.repository = repository;
  }

  @Override
  public Recipe createRecipe(RecipeDto recipe) {
    LOG.info("Inserting recipe {}", recipe);
    return repository.insert(recipe);
  }

  @Override
  public ImmutableSet<Recipe> getRecipes(ImmutableSet<String> ids) {
    return repository.findByIds(ids);
  }

  @Override
  public Recipe updateRecipe(String id, RecipeDto recipeDto) {
    LOG.info("Updating recipe with id='{}' to have fields {}", id, recipeDto);

    Recipe recipe = Recipe.builder()
        .id(id)
        .name(recipeDto.getName())
        .ingredients(recipeDto.getIngredients())
        .preparationInstructions(recipeDto.getPreparationInstructions())
        .servingsNumber(recipeDto.getServingsNumber())
        .recipeTags(recipeDto.getRecipeTags())
        .build();

    Optional<Recipe> updateResult = repository.update(recipe);
    if (updateResult.isEmpty()) {
      throw new IllegalArgumentException(String.format("No recipe with id='%s' to update was found", id));
    }

    return updateResult.orElseThrow();
  }

  @Override
  public void deleteRecipe(String id) {
    LOG.info("Deleting recipe with id='{}'", id);

    repository.delete(id);
  }
}
