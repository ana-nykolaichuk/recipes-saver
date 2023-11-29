package abnamro.anastasiia.recipessaver.recipe.logic.data;

import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import java.util.Optional;
import java.util.UUID;

final class RecipeMapper {
  private RecipeMapper() {
  }

  static RecipeDocument toRecipeDocument(RecipeDto creationRequest) {
    return RecipeDocument.builder()
        .id(UUID.randomUUID().toString())
        .name(creationRequest.getName())
        .ingredients(creationRequest.getIngredients())
        .preparationInstructions(creationRequest.getPreparationInstructions())
        .servingsNumber(creationRequest.getServingsNumber())
        .recipeTags(creationRequest.getRecipeTags())
        .build();
  }

  static RecipeDocument toRecipeDocument(Recipe recipe) {
    return RecipeDocument.builder()
        .id(recipe.getId())
        .name(recipe.getName())
        .ingredients(recipe.getIngredients())
        .preparationInstructions(recipe.getPreparationInstructions())
        .servingsNumber(recipe.getServingsNumber())
        .recipeTags(recipe.getRecipeTags())
        .build();
  }

  static Recipe toRecipe(RecipeDocument recipeDocument) {
    return Recipe.builder()
        .id(recipeDocument.getId())
        .name(recipeDocument.getName())
        .ingredients(recipeDocument.getIngredients())
        .preparationInstructions(recipeDocument.getPreparationInstructions())
        .servingsNumber(recipeDocument.getServingsNumber())
        .recipeTags(recipeDocument.getRecipeTags())
        .build();
  }

  static Optional<Recipe> toRecipe(Optional<RecipeDocument> recipeDocument) {
    return recipeDocument.map(RecipeMapper::toRecipe);
  }
}
