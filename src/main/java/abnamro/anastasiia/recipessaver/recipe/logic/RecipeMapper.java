package abnamro.anastasiia.recipessaver.recipe.logic;

import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.logic.data.RecipeDocument;

public final class RecipeMapper {
  private RecipeMapper() {
  }

  public static Recipe toRecipe(String id, RecipeDto recipeDto) {
    return Recipe.builder()
        .id(id)
        .name(recipeDto.getName())
        .ingredients(recipeDto.getIngredients())
        .preparationInstructions(recipeDto.getPreparationInstructions())
        .servingsNumber(recipeDto.getServingsNumber())
        .recipeTags(recipeDto.getRecipeTags())
        .build();
  }

  public static RecipeDocument toRecipeDocument(Recipe recipe) {
    return RecipeDocument.builder()
        .id(recipe.getId())
        .name(recipe.getName())
        .ingredients(recipe.getIngredients())
        .preparationInstructions(recipe.getPreparationInstructions())
        .servingsNumber(recipe.getServingsNumber())
        .recipeTags(recipe.getRecipeTags())
        .build();
  }

  public static Recipe toRecipe(RecipeDocument recipeDocument) {
    return Recipe.builder()
        .id(recipeDocument.getId())
        .name(recipeDocument.getName())
        .ingredients(recipeDocument.getIngredients())
        .preparationInstructions(recipeDocument.getPreparationInstructions())
        .servingsNumber(recipeDocument.getServingsNumber())
        .recipeTags(recipeDocument.getRecipeTags())
        .build();
  }
}
