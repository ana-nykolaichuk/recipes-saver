package abnamro.anastasiia.recipessaver.recipe.logic;

import static abnamro.anastasiia.recipessaver.recipe.api.Ingredient.IngredientAmountType.TBSP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import abnamro.anastasiia.recipessaver.recipe.api.FindRecipesFilter;
import abnamro.anastasiia.recipessaver.recipe.api.Ingredient;
import abnamro.anastasiia.recipessaver.recipe.api.Ingredient.Amount;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeTag;
import abnamro.anastasiia.recipessaver.recipe.logic.data.RecipeRepository;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class RecipeServiceImplTest {
  private static final String RECIPE_ID = "recipeId";
  private static final Ingredient INGREDIENT = Ingredient.builder()
      .name("ingredientName")
      .amount(Amount.builder()
          .amount(1)
          .type(TBSP)
          .build())
      .build();
  private static final Recipe RECIPE = Recipe.builder()
      .id(RECIPE_ID)
      .name("recipeName")
      .ingredients(ImmutableSet.of(INGREDIENT))
      .preparationInstructions("preparationInstructions")
      .servingsNumber(1)
      .recipeTags(ImmutableSet.of(RecipeTag.BEGINNER_FRIENDLY))
      .build();

  private static final RecipeDto RECIPE_DTO = RecipeDto.builder()
      .name("recipeName")
      .ingredients(ImmutableSet.of(INGREDIENT))
      .preparationInstructions("preparationInstructions")
      .servingsNumber(1)
      .recipeTags(ImmutableSet.of(RecipeTag.BEGINNER_FRIENDLY))
      .build();

  @Test
  public void createRecipe() {
    RecipeRepository repository = mock();
    when(repository.insert(any(Recipe.class))).thenReturn(RECIPE);

    Recipe actual = new RecipeServiceImpl(repository).createRecipe(RECIPE_DTO);

    assertThat(actual).isEqualTo(RECIPE);
  }

  @Test
  public void getRecipes() {
    RecipeRepository repository = mock();
    when(repository.findByIds(ImmutableSet.of(RECIPE_ID))).thenReturn(ImmutableSet.of(RECIPE));

    ImmutableSet<Recipe> actual = new RecipeServiceImpl(repository).getRecipes(ImmutableSet.of(RECIPE_ID));

    assertThat(actual).containsExactly(RECIPE);
  }

  @Test
  public void updateRecipe() {
    RecipeRepository repository = mock();
    when(repository.update(RECIPE)).thenReturn(Optional.of(RECIPE));

    Recipe actual = new RecipeServiceImpl(repository).updateRecipe(RECIPE_ID, RECIPE_DTO);

    assertThat(actual).isEqualTo(RECIPE);
  }

  @Test
  public void updateRecipeNoRecipeFound() {
    RecipeRepository repository = mock();
    when(repository.update(RECIPE)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> new RecipeServiceImpl(repository).updateRecipe(RECIPE_ID, RECIPE_DTO))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(String.format("No recipe with id='%s' to update was found", RECIPE_ID));
  }

  @Test
  public void deleteRecipe() {
    RecipeRepository repository = mock();

    new RecipeServiceImpl(repository).deleteRecipe(RECIPE_ID);

    verify(repository).delete(RECIPE_ID);
  }

  @Test
  public void findRecipes() {
    FindRecipesFilter filters = FindRecipesFilter.builder()
        .name(Optional.of("Recipe Name"))
        .build();
    RecipeRepository repository = mock();
    when(repository.find(filters)).thenReturn(ImmutableSet.of(RECIPE));

    ImmutableSet<Recipe> actual = new RecipeServiceImpl(repository).find(filters);

    assertThat(actual).containsExactly(RECIPE);
  }
}