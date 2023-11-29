package abnamro.anastasiia.recipessaver.recipe.logic.data;

import static abnamro.anastasiia.recipessaver.recipe.logic.data.RecipeMapper.toRecipe;
import static abnamro.anastasiia.recipessaver.recipe.logic.data.RecipeMapper.toRecipeDocument;
import static com.google.common.collect.ImmutableSet.toImmutableSet;

import abnamro.anastasiia.recipessaver.recipe.api.Recipe;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeDto;
import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepository {
  private final MongoTemplate template;

  public RecipeRepository(MongoTemplate template) {
    this.template = template;
  }

  public Recipe insert(RecipeDto creationRequest) {
    return toRecipe(
        template.insert(
            toRecipeDocument(creationRequest)));
  }

  public Optional<Recipe> update(Recipe recipe) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(recipe.getId()));

    Optional<RecipeDocument> updatedDocument =
        Optional.ofNullable(
            template.findAndReplace(query, toRecipeDocument(recipe), new FindAndReplaceOptions().returnNew()));
    return toRecipe(updatedDocument);
  }

  public ImmutableSet<Recipe> findByIds(ImmutableSet<String> ids) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").in(ids));

    return template.find(query, RecipeDocument.class)
        .stream()
        .map(RecipeMapper::toRecipe)
        .collect(toImmutableSet());
  }

  public void delete(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));

    template.remove(query, RecipeDocument.class);
  }
}
