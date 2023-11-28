package abnamro.anastasiia.recipessaver.recipe.web;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeCreationRequest;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeService;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeUpdateRequest;
import com.google.common.collect.ImmutableSet;
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
  public Recipe createRecipe(@RequestBody RecipeCreationRequest request) {
    return recipeService.createRecipe(request);
  }

  @GetMapping
  public ImmutableSet<Recipe> getRecipes(@RequestParam(required = false) Set<String> ids) {
    return recipeService.getRecipes(ImmutableSet.copyOf(ids));
  }

  @PutMapping
  public Recipe updateRecipe(@RequestBody RecipeUpdateRequest request) {
    return recipeService.updateRecipe(request.getRecipeId(), request.getRecipe());
  }

  @DeleteMapping
  @ResponseStatus(NO_CONTENT)
  public void deleteRecipe(@RequestParam String id) {
    recipeService.deleteRecipe(id);
  }
}
