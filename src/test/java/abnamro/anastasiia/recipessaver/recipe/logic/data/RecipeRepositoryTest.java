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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(RecipeRepository.class)
class RecipeRepositoryTest {
  private static final String RECIPE_NAME = "name";
  private static final Ingredient INGREDIENT = Ingredient.builder()
      .name("ingredient name")
      .amountDescription("amount description")
      .build();
  private static final String PREPARATION_INSTRUCTIONS = "preparation instructions";
  private static final int SERVINGS_NUMBER = 1;
  private static final ImmutableSet<RecipeTag> TAGS = ImmutableSet.of(BEGINNER_FRIENDLY);
  @Autowired
  private RecipeRepository repository;

  @Test
  public void insert() {
    RecipeDto creationRequest = RecipeDto.builder()
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    Recipe actual = repository.insert(creationRequest);

    assertThat(actual).extracting(Recipe::getId).isNotNull();
    assertThat(actual).extracting(Recipe::getName).isEqualTo(RECIPE_NAME);
    assertThat(actual).extracting(Recipe::getIngredients).isEqualTo(ImmutableSet.of(INGREDIENT));
    assertThat(actual).extracting(Recipe::getPreparationInstructions).isEqualTo(PREPARATION_INSTRUCTIONS);
    assertThat(actual).extracting(Recipe::getServingsNumber).isEqualTo(SERVINGS_NUMBER);
    assertThat(actual).extracting(Recipe::getRecipeTags).isEqualTo(TAGS);
  }

  @Test
  public void update() {
    RecipeDto creationRequest = RecipeDto.builder()
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    Recipe inserted = repository.insert(creationRequest);
    inserted.setName("new name");

    Optional<Recipe> actual = repository.update(inserted);
    assertThat(actual).hasValue(inserted);
  }

  @Test
  public void updateNonExistent() {
    Recipe recipe = Recipe.builder()
        .id("fake id")
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    Optional<Recipe> actual = repository.update(recipe);
    assertThat(actual).isEmpty();
  }

  @Test
  public void findByIds() {
    RecipeDto creationRequest = RecipeDto.builder()
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    Recipe inserted = repository.insert(creationRequest);

    ImmutableSet<Recipe> actual = repository.findByIds(ImmutableSet.of(inserted.getId(), "fake id"));
    assertThat(actual).containsExactly(inserted);
  }

  @Test
  public void delete() {
    RecipeDto creationRequest = RecipeDto.builder()
        .name(RECIPE_NAME)
        .ingredients(ImmutableSet.of(INGREDIENT))
        .preparationInstructions(PREPARATION_INSTRUCTIONS)
        .servingsNumber(SERVINGS_NUMBER)
        .recipeTags(TAGS)
        .build();

    Recipe inserted = repository.insert(creationRequest);

    repository.delete(inserted.getId());

    ImmutableSet<Recipe> actual = repository.findByIds(ImmutableSet.of(inserted.getId()));
    assertThat(actual).isEmpty();
  }
}