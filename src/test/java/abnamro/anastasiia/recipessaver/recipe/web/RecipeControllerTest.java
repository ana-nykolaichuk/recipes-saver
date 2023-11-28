package abnamro.anastasiia.recipessaver.recipe.web;

import static org.mockito.Mockito.when;

import abnamro.anastasiia.recipessaver.recipe.api.Ingredient;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeCreationRequest;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeService;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeTag;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeUpdateRequest;
import com.google.common.collect.ImmutableSet;
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
  private static final Recipe RECIPE = new Recipe(
      RECIPE_ID,
      "recipeName",
      ImmutableSet.of(
          new Ingredient("ingredientName", "amountDescription")),
      "preparationInstructions",
      1,
      ImmutableSet.of(RecipeTag.BEGINNER_FRIENDLY));

  @MockBean
  private RecipeService service;
  @Autowired
  private WebTestClient webClient;

  @Test
  public void createRecipe() {
    RecipeCreationRequest request =
        new RecipeCreationRequest(
            "recipeName",
            ImmutableSet.of(
                new Ingredient("ingredientName", "amountDescription")),
            "preparationInstructions",
            1,
            ImmutableSet.of(RecipeTag.BEGINNER_FRIENDLY));
    when(service.createRecipe(request)).thenReturn(RECIPE);

    webClient
        .post()
        .uri("/recipe")
        .bodyValue(request)
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
    when(service.updateRecipe(RECIPE_ID, RECIPE)).thenReturn(RECIPE);

    webClient
        .put()
        .uri("/recipe")
        .bodyValue(new RecipeUpdateRequest(RECIPE_ID, RECIPE))
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
}