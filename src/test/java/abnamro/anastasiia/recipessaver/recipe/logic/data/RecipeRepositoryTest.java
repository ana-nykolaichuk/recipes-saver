package abnamro.anastasiia.recipessaver.recipe.logic.data;

import static abnamro.anastasiia.recipessaver.recipe.api.Ingredient.IngredientAmountType.KG;
import static abnamro.anastasiia.recipessaver.recipe.api.Ingredient.IngredientAmountType.TBSP;
import static abnamro.anastasiia.recipessaver.recipe.api.RecipeTag.BEGINNER_FRIENDLY;
import static abnamro.anastasiia.recipessaver.recipe.api.RecipeTag.CHRISTMAS;
import static abnamro.anastasiia.recipessaver.recipe.api.RecipeTag.VEGETARIAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import abnamro.anastasiia.recipessaver.recipe.api.FindRecipesFilter;
import abnamro.anastasiia.recipessaver.recipe.api.Ingredient;
import abnamro.anastasiia.recipessaver.recipe.api.Ingredient.Amount;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeTag;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(RecipeRepository.class)
class RecipeRepositoryTest {
  private static final String RECIPE_ID1 = "testRecipe1";
  private static final String RECIPE_ID2 = "testRecipe2";
  private static final String RECIPE_ID3 = "testRecipe3";
  private static final String NON_EXISTENT_ID = "fakeId";
  private static final String RECIPE_NAME = "name";
  private static final Ingredient BASE_INGREDIENT = Ingredient.builder()
      .name("ingredient name")
      .amount(Amount.builder()
          .amount(0.5)
          .type(TBSP)
          .build())
      .build();
  private static final Ingredient POTATO_INGREDIENT = Ingredient.builder()
      .name("boring potato")
      .amount(Amount.builder()
          .amount(1)
          .type(KG)
          .build())
      .build();
  private static final String PREPARATION_INSTRUCTIONS = "preparation instructions";
  private static final int SERVINGS_NUMBER = 1;
  private static final ImmutableSet<RecipeTag> TAGS = ImmutableSet.of(BEGINNER_FRIENDLY, VEGETARIAN);
  private static final Recipe RECIPE = Recipe.builder()
      .id(RECIPE_ID1)
      .name(RECIPE_NAME)
      .ingredients(ImmutableSet.of(BASE_INGREDIENT))
      .preparationInstructions(PREPARATION_INSTRUCTIONS)
      .servingsNumber(SERVINGS_NUMBER)
      .recipeTags(TAGS)
      .build();
  @Autowired
  private RecipeRepository repository;

  @AfterEach
  public void cleanUp() {
    repository.find(FindRecipesFilter.builder().build())
        .stream()
        .map(Recipe::getId)
        .forEach(repository::delete);
  }

  @Test
  public void insert() {
    Recipe actual = repository.insert(RECIPE);

    assertThat(actual).extracting(Recipe::getId).isEqualTo(RECIPE_ID1);
    assertThat(actual).extracting(Recipe::getName).isEqualTo(RECIPE_NAME);
    assertThat(actual).extracting(Recipe::getIngredients).isEqualTo(ImmutableSet.of(BASE_INGREDIENT));
    assertThat(actual).extracting(Recipe::getPreparationInstructions).isEqualTo(PREPARATION_INSTRUCTIONS);
    assertThat(actual).extracting(Recipe::getServingsNumber).isEqualTo(SERVINGS_NUMBER);
    assertThat(actual).extracting(Recipe::getRecipeTags).isEqualTo(TAGS);
  }

  @Test
  public void update() {
    Recipe inserted = repository.insert(RECIPE);
    Recipe withUpdatedName = inserted.withName("new name");

    Optional<Recipe> actual = repository.update(withUpdatedName);
    assertThat(actual).hasValue(withUpdatedName);
  }

  @Test
  public void updateNonExistent() {
    Recipe recipe = RECIPE.withId(NON_EXISTENT_ID);

    Optional<Recipe> actual = repository.update(recipe);
    assertThat(actual).isEmpty();
  }

  @Test
  public void findByIds() {
    Recipe inserted = repository.insert(RECIPE);

    ImmutableSet<Recipe> actual = repository.findByIds(ImmutableSet.of(inserted.getId(), NON_EXISTENT_ID));
    assertThat(actual).containsExactly(inserted);
  }

  @Test
  public void delete() {
    Recipe inserted = repository.insert(RECIPE);

    repository.delete(inserted.getId());

    ImmutableSet<Recipe> actual = repository.findByIds(ImmutableSet.of(inserted.getId()));
    assertThat(actual).isEmpty();
  }

  private static Stream<Arguments> findRecipesTestCases() {
    /* filters, existingRecipes, expectedRecipes */
    return Stream.of(
        // No filters, return all recipes
        arguments(
            FindRecipesFilter.builder().build(),
            ImmutableSet.of(RECIPE),
            ImmutableSet.of(RECIPE)
        ),
        // Filter by name
        arguments(
            FindRecipesFilter.builder()
                .name(Optional.of(RECIPE.getName()))
                .build(),
            ImmutableSet.of(
                RECIPE,
                RECIPE.withId(RECIPE_ID2)
                    .withName("New name")),
            ImmutableSet.of(RECIPE)
        ),
        // Filter by preparation instructions
        arguments(
            FindRecipesFilter.builder()
                .preparationInstructions(Optional.of(RECIPE.getPreparationInstructions().substring(5, 10)))
                .build(),
            ImmutableSet.of(
                RECIPE,
                RECIPE.withId(RECIPE_ID2)
                    .withPreparationInstructions("Completely different")),
            ImmutableSet.of(RECIPE)
        ),
        // Filter by number of servings
        arguments(
            FindRecipesFilter.builder()
                .numberOfServings(Optional.of(RECIPE.getServingsNumber()))
                .build(),
            ImmutableSet.of(
                RECIPE,
                RECIPE.withId(RECIPE_ID2)
                    .withServingsNumber(RECIPE.getServingsNumber() + 1)),
            ImmutableSet.of(RECIPE)
        ),
        // Filter by included tags
        arguments(
            FindRecipesFilter.builder()
                .tagsIncluded(TAGS)
                .build(),
            ImmutableSet.of(
                RECIPE,
                RECIPE.withId(RECIPE_ID2)
                    .withRecipeTags(ImmutableSet.of(CHRISTMAS))),
            ImmutableSet.of(RECIPE)
        ),
        // Filter by excluded tags
        arguments(
            FindRecipesFilter.builder()
                .tagsExcluded(ImmutableSet.of(CHRISTMAS))
                .build(),
            ImmutableSet.of(
                RECIPE,
                RECIPE.withId(RECIPE_ID2)
                    .withRecipeTags(ImmutableSet.of(CHRISTMAS))),
            ImmutableSet.of(RECIPE)
        ),
        // Filter by included ingredients
        arguments(
            FindRecipesFilter.builder()
                .ingredientsIncluded(ImmutableSet.of(BASE_INGREDIENT.getName()))
                .build(),
            ImmutableSet.of(
                RECIPE,
                RECIPE.withId(RECIPE_ID2)
                    .withIngredients(ImmutableSet.of(POTATO_INGREDIENT))),
            ImmutableSet.of(RECIPE)
        ),
        // Filter by excluded ingredients
        arguments(
            FindRecipesFilter.builder()
                .ingredientsExcluded(ImmutableSet.of(POTATO_INGREDIENT.getName()))
                .build(),
            ImmutableSet.of(
                RECIPE,
                RECIPE.withId(RECIPE_ID2)
                    .withIngredients(ImmutableSet.of(POTATO_INGREDIENT))),
            ImmutableSet.of(RECIPE)
        ),
        // Filter by combination of filters
        arguments(
            FindRecipesFilter.builder()
                .ingredientsExcluded(ImmutableSet.of(POTATO_INGREDIENT.getName()))
                .name(Optional.of(RECIPE.getName()))
                .numberOfServings(Optional.of(RECIPE.getServingsNumber()))
                .tagsIncluded(ImmutableSet.of(BEGINNER_FRIENDLY))
                .build(),
            ImmutableSet.of(
                RECIPE,
                RECIPE.withId(RECIPE_ID2)
                    .withIngredients(ImmutableSet.of(POTATO_INGREDIENT))
                    .withRecipeTags(ImmutableSet.of(VEGETARIAN)),
                RECIPE.withId(RECIPE_ID3)
                    .withName("New name")
                    .withServingsNumber(RECIPE.getServingsNumber() + 1)),
            ImmutableSet.of(RECIPE)
        )
    );
  }

  @ParameterizedTest
  @MethodSource("findRecipesTestCases")
  public void find(FindRecipesFilter filters,
                   ImmutableSet<Recipe> existingRecipes,
                   ImmutableSet<Recipe> expectedRecipes) {
    existingRecipes.forEach(recipe -> repository.insert(recipe));

    ImmutableSet<Recipe> recipes = repository.find(filters);
    assertThat(recipes).isEqualTo(expectedRecipes);
  }
}
