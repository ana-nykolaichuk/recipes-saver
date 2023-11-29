package abnamro.anastasiia.recipessaver.recipe.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import abnamro.anastasiia.recipessaver.recipe.api.Ingredient;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeTag;
import abnamro.anastasiia.recipessaver.recipe.logic.data.RecipeRepository;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class RecipeServiceImplTest {
  private static final String RECIPE_ID = "recipeId";
  private static final Recipe RECIPE = Recipe.builder()
      .id(RECIPE_ID)
      .name("recipeName")
      .ingredients(ImmutableSet.of(
          new Ingredient("ingredientName", "amountDescription")))
      .preparationInstructions("preparationInstructions")
      .servingsNumber(1)
      .recipeTags(ImmutableSet.of(RecipeTag.BEGINNER_FRIENDLY))
      .build();

  private static final RecipeDto RECIPE_DTO = RecipeDto.builder()
      .name("recipeName")
      .ingredients(ImmutableSet.of(
          new Ingredient("ingredientName", "amountDescription")))
      .preparationInstructions("preparationInstructions")
      .servingsNumber(1)
      .recipeTags(ImmutableSet.of(RecipeTag.BEGINNER_FRIENDLY))
      .build();

  @Test
  public void createRecipe() {
    RecipeRepository repository = mock();
    when(repository.insert(RECIPE_DTO)).thenReturn(RECIPE);

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
}