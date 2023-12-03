package abnamro.anastasiia.recipessaver.recipe.logic;

import static abnamro.anastasiia.recipessaver.recipe.logic.RecipeMapper.toRecipe;

import abnamro.anastasiia.recipessaver.recipe.api.FindRecipesFilter;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeService;
import abnamro.anastasiia.recipessaver.recipe.logic.data.RecipeRepository;
import com.google.common.collect.ImmutableSet;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
final class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository repository;

  RecipeServiceImpl(RecipeRepository repository) {
    this.repository = repository;
  }

  @Override
  public Recipe createRecipe(RecipeDto recipe) {
    log.info("Inserting recipe {}", recipe);
    return repository.insert(toRecipe(UUID.randomUUID().toString(), recipe));
  }

  @Override
  public ImmutableSet<Recipe> getRecipes(ImmutableSet<String> ids) {
    return repository.findByIds(ids);
  }

  @Override
  public Recipe updateRecipe(String id, RecipeDto recipeDto) {
    log.info("Updating recipe with id='{}' to have fields {}", id, recipeDto);

    return repository.update(toRecipe(id, recipeDto))
        .orElseThrow(() ->
            new IllegalArgumentException(String.format("No recipe with id='%s' to update was found", id)));
  }

  @Override
  public void deleteRecipe(String id) {
    log.info("Deleting recipe with id='{}'", id);

    repository.delete(id);
  }

  @Override
  public ImmutableSet<Recipe> find(FindRecipesFilter filter) {
    return repository.find(filter);
  }
}
