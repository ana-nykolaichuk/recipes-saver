package abnamro.anastasiia.recipessaver.recipe.web;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import abnamro.anastasiia.recipessaver.recipe.api.FindRecipesFilter;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeService;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeUpdateRequest;
import com.google.common.collect.ImmutableSet;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Set;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public final class RecipeController {
  private final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @PostMapping
  @Operation(summary = "Creates a new recipe")
  public Recipe createRecipe(@RequestBody RecipeDto recipeDto) {
    return recipeService.createRecipe(recipeDto);
  }

  @GetMapping
  @Operation(summary = "Returns recipe by IDs, all recipes if no ID provided")
  public ImmutableSet<Recipe> getRecipes(@RequestParam(required = false) Set<String> ids) {
    ImmutableSet<String> idsToSearch = ids == null
        ? ImmutableSet.of()
        : ImmutableSet.copyOf(ids);
    return recipeService.getRecipes(idsToSearch);
  }

  @PutMapping
  @Operation(summary = "Updates the recipe")
  public Recipe updateRecipe(@RequestBody RecipeUpdateRequest request) {
    return recipeService.updateRecipe(request.getRecipeId(), request.getRecipe());
  }

  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "Deletes the recipe")
  public void deleteRecipe(@RequestParam String id) {
    recipeService.deleteRecipe(id);
  }

  @PostMapping("/find")
  @Operation(summary = "Returns recipes based on filters")
  public ImmutableSet<Recipe> find(@RequestBody FindRecipesFilter filter) {
    return recipeService.find(filter);
  }
}
