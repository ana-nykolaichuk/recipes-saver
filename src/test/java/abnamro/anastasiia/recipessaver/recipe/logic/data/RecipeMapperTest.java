package abnamro.anastasiia.recipessaver.recipe.logic.data;

import static abnamro.anastasiia.recipessaver.recipe.api.Ingredient.IngredientAmountType.GR;
import static abnamro.anastasiia.recipessaver.recipe.api.RecipeTag.BEGINNER_FRIENDLY;
import static org.assertj.core.api.Assertions.assertThat;

import abnamro.anastasiia.recipessaver.recipe.api.Ingredient;
import abnamro.anastasiia.recipessaver.recipe.api.Ingredient.Amount;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeTag;
import abnamro.anastasiia.recipessaver.recipe.logic.RecipeMapper;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

class RecipeMapperTest {
  private static final String RECIPE_ID = "id";
  private static final String RECIPE_NAME = "name";
  private static final Ingredient INGREDIENT = Ingredient.builder()
      .name("ingredient name")
      .amount(Amount.builder()
          .amount(200)
          .type(GR)
          .build())
      .build();
  private static final String PREPARATION_INSTRUCTIONS = "preparation instructions";
  private static final int SERVINGS_NUMBER = 1;
  private static final ImmutableSet<RecipeTag> TAGS = ImmutableSet.of(BEGINNER_FRIENDLY);

  @Test
  public void toRecipeFromCreationRequest() {
    RecipeDto recipeDto = RecipeDto.builder()
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    Recipe actual = RecipeMapper.toRecipe(RECIPE_ID, recipeDto);
    assertThat(actual).extracting(Recipe::getId).isEqualTo(RECIPE_ID);
    assertThat(actual).extracting(Recipe::getName).isEqualTo(RECIPE_NAME);
    assertThat(actual).extracting(Recipe::getIngredients).isEqualTo(ImmutableSet.of(INGREDIENT));
    assertThat(actual).extracting(Recipe::getPreparationInstructions).isEqualTo(PREPARATION_INSTRUCTIONS);
    assertThat(actual).extracting(Recipe::getServingsNumber).isEqualTo(SERVINGS_NUMBER);
    assertThat(actual).extracting(Recipe::getRecipeTags).isEqualTo(TAGS);
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
}
