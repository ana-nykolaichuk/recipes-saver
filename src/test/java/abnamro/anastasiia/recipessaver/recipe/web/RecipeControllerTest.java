package abnamro.anastasiia.recipessaver.recipe.web;

import static abnamro.anastasiia.recipessaver.recipe.api.Ingredient.IngredientAmountType.TBSP;
import static abnamro.anastasiia.recipessaver.recipe.api.RecipeTag.BEGINNER_FRIENDLY;
import static abnamro.anastasiia.recipessaver.recipe.api.RecipeTag.VEGETARIAN;
import static org.mockito.Mockito.when;

import abnamro.anastasiia.recipessaver.recipe.api.FindRecipesFilter;
import abnamro.anastasiia.recipessaver.recipe.api.Ingredient;
import abnamro.anastasiia.recipessaver.recipe.api.Ingredient.Amount;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeService;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeUpdateRequest;
import abnamro.anastasiia.recipessaver.recipe.logic.data.RecipeRepository;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
final class RecipeControllerTest {
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
      .recipeTags(ImmutableSet.of(BEGINNER_FRIENDLY))
      .build();

  private static final RecipeDto RECIPE_DTO = RecipeDto.builder()
      .name("recipeName")
      .ingredients(ImmutableSet.of(INGREDIENT))
      .preparationInstructions("preparationInstructions")
      .servingsNumber(1)
      .recipeTags(ImmutableSet.of(BEGINNER_FRIENDLY))
      .build();

  @MockBean
  private RecipeService service;
  @MockBean
  private RecipeRepository repository;
  @Autowired
  private WebTestClient webClient;

  @Test
  public void createRecipe() {
    when(service.createRecipe(RECIPE_DTO)).thenReturn(RECIPE);

    webClient
        .post()
        .uri("/recipe")
        .bodyValue(RECIPE_DTO)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Recipe.class)
        .isEqualTo(RECIPE);
  }

  @Test
  public void getRecipes() {
    when(service.getRecipes(ImmutableSet.of(RECIPE_ID))).thenReturn(ImmutableSet.of(RECIPE));

    webClient
        .get()
        .uri("/recipe?ids={ids}", RECIPE_ID)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Recipe.class)
        .hasSize(1)
        .contains(RECIPE);
  }

  @Test
  public void updateRecipe() {
    when(service.updateRecipe(RECIPE_ID, RECIPE_DTO)).thenReturn(RECIPE);

    webClient
        .put()
        .uri("/recipe")
        .bodyValue(new RecipeUpdateRequest(RECIPE_ID, RECIPE_DTO))
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Recipe.class)
        .isEqualTo(RECIPE);
  }

  @Test
  public void deleteRecipe() {
    webClient
        .delete()
        .uri("/recipe?id={recipeId}", RECIPE_ID)
        .exchange()
        .expectStatus()
        .isNoContent();
  }

  @Test
  public void findRecipe() {
    FindRecipesFilter filter = FindRecipesFilter.builder()
        .tagsIncluded(ImmutableSet.of(VEGETARIAN))
        .preparationInstructions(Optional.of("oven"))
        .build();

    when(service.find(filter)).thenReturn(ImmutableSet.of(RECIPE));

    webClient
        .post()
        .uri("/recipe/find")
        .bodyValue(filter)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Recipe.class)
        .hasSize(1)
        .contains(RECIPE);
  }
}