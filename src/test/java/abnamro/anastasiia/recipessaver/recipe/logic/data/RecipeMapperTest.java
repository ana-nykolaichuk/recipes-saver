package abnamro.anastasiia.recipessaver.recipe.logic.data;

import static abnamro.anastasiia.recipessaver.recipe.api.RecipeTag.BEGINNER_FRIENDLY;
import static org.assertj.core.api.Assertions.assertThat;

import abnamro.anastasiia.recipessaver.recipe.api.Ingredient;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeTag;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class RecipeMapperTest {
  private static final String RECIPE_ID = "id";
  private static final String RECIPE_NAME = "name";
  private static final Ingredient INGREDIENT = Ingredient.builder()
      .name("ingredient name")
      .amountDescription("amount description")
      .build();
  private static final String PREPARATION_INSTRUCTIONS = "preparation instructions";
  private static final int SERVINGS_NUMBER = 1;
  private static final ImmutableSet<RecipeTag> TAGS = ImmutableSet.of(BEGINNER_FRIENDLY);

  @Test
  public void toRecipeDocumentFromCreationRequest() {
    RecipeDto creationRequest = RecipeDto.builder()
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    RecipeDocument actual = RecipeMapper.toRecipeDocument(creationRequest);
    assertThat(actual).extracting(RecipeDocument::getName).isEqualTo(RECIPE_NAME);
    assertThat(actual).extracting(RecipeDocument::getIngredients).isEqualTo(ImmutableSet.of(INGREDIENT));
    assertThat(actual).extracting(RecipeDocument::getPreparationInstructions).isEqualTo(PREPARATION_INSTRUCTIONS);
    assertThat(actual).extracting(RecipeDocument::getServingsNumber).isEqualTo(SERVINGS_NUMBER);
    assertThat(actual).extracting(RecipeDocument::getRecipeTags).isEqualTo(TAGS);
  }

  @Test
  public void toRecipeDocumentFromRecipe() {
    Recipe recipe = Recipe.builder()
        .id(RECIPE_ID)
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    RecipeDocument expected = RecipeDocument.builder()
        .id(RECIPE_ID)
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    assertThat(RecipeMapper.toRecipeDocument(recipe)).isEqualTo(expected);
  }

  @Test
  public void toRecipe() {
    RecipeDocument recipeDocument = RecipeDocument.builder()
        .id(RECIPE_ID)
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    Recipe expected = Recipe.builder()
        .id(RECIPE_ID)
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    assertThat(RecipeMapper.toRecipe(recipeDocument)).isEqualTo(expected);
  }

  @Test
  public void toRecipeEmptyOptional() {
    assertThat(RecipeMapper.toRecipe(Optional.empty())).isEmpty();
  }
}