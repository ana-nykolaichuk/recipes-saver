package abnamro.anastasiia.recipessaver.recipe.web;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import abnamro.anastasiia.recipessaver.recipe.api.RecipeCreationRequest;
import abnamro.anastasiia.recipessaver.recipe.api.RecipeUpdateRequest;
import java.util.Set;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
public final class RecipeController {
    @PostMapping
    public void createRecipe(@RequestBody RecipeCreationRequest request) {
        throw new UnsupportedOperationException("Sit tight, it will be implemented soon.");
    }

    @GetMapping
    public String getRecipes(@RequestParam(required = false) Set<String> ids) {
        throw new UnsupportedOperationException("Sit tight, it will be implemented soon.");
    }

    @PutMapping
    public void updateRecipe(@RequestBody RecipeUpdateRequest request) {
        throw new UnsupportedOperationException("Sit tight, it will be implemented soon.");
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteRecipe(@RequestParam String id) {
        throw new UnsupportedOperationException("Sit tight, it will be implemented soon.");
    }
}
