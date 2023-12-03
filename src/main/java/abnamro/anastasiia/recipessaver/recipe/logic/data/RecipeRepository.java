package abnamro.anastasiia.recipessaver.recipe.logic.data;

import static abnamro.anastasiia.recipessaver.recipe.logic.RecipeMapper.toRecipe;
import static abnamro.anastasiia.recipessaver.recipe.logic.RecipeMapper.toRecipeDocument;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import abnamro.anastasiia.recipessaver.recipe.api.FindRecipesFilter;
import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.logic.RecipeMapper;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepository {
  private static final String ID_FIELD = "_id";
  private static final String NAME_FIELD = "name";
  private static final String PREPARATION_INSTRUCTIONS_FIELD = "preparationInstructions";
  private static final String SERVINGS_NUMBER_FIELD = "servingsNumber";
  private static final String RECIPE_TAGS_FIELD = "recipeTags";
  private static final String INGREDIENTS_NAME_FIELD = "ingredients.name";
  private final MongoTemplate template;

  public RecipeRepository(MongoTemplate template) {
    this.template = template;
  }

  public Recipe insert(Recipe recipe) {
    return toRecipe(template.insert(toRecipeDocument(recipe)));
  }

  public Optional<Recipe> update(Recipe recipe) {
    Query query = new Query();
    query.addCriteria(where(ID_FIELD).is(recipe.getId()));

    return Optional.ofNullable(
            template.findAndReplace(query, toRecipeDocument(recipe), new FindAndReplaceOptions().returnNew()))
        .map(RecipeMapper::toRecipe);
  }

  public ImmutableSet<Recipe> findByIds(ImmutableSet<String> ids) {
    Query query = new Query();

    if (!ids.isEmpty()) {
      query.addCriteria(where(ID_FIELD).in(ids));
    }

    return template.find(query, RecipeDocument.class)
        .stream()
        .map(RecipeMapper::toRecipe)
        .collect(toImmutableSet());
  }

  public void delete(String id) {
    Query query = new Query();
    query.addCriteria(where(ID_FIELD).is(id));

    template.remove(query, RecipeDocument.class);
  }

  public ImmutableSet<Recipe> find(FindRecipesFilter filters) {
    Query query = new Query();

    filters.getName().ifPresent(name -> query.addCriteria(where(NAME_FIELD).is(name)));

    filters.getPreparationInstructions().ifPresent(instructions ->
        query.addCriteria(where(PREPARATION_INSTRUCTIONS_FIELD)
            .regex(String.format(".*%s.*", instructions), "i")));

    filters.getNumberOfServings()
        .ifPresent(servings -> query.addCriteria(where(SERVINGS_NUMBER_FIELD).is(servings)));

    if (!filters.getTagsIncluded().isEmpty()) {
      query.addCriteria(where(RECIPE_TAGS_FIELD).in(filters.getTagsIncluded()));
    }

    if (!filters.getTagsExcluded().isEmpty()) {
      query.addCriteria(where(RECIPE_TAGS_FIELD).not().in(filters.getTagsExcluded()));
    }

    if (!filters.getIngredientsIncluded().isEmpty()) {
      query.addCriteria(where(INGREDIENTS_NAME_FIELD).in(filters.getIngredientsIncluded()));
    }

    if (!filters.getIngredientsExcluded().isEmpty()) {
      query.addCriteria(where(INGREDIENTS_NAME_FIELD).not().in(filters.getIngredientsExcluded()));
    }

    return template.find(query, RecipeDocument.class)
        .stream()
        .map(RecipeMapper::toRecipe)
        .collect(toImmutableSet());
  }
}
