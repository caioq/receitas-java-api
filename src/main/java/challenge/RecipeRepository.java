package challenge;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
    Optional<Recipe> findById(String id);

    List<Recipe> findRecipesByIngredientsOrderByTitleAsc (String ingredient);

    List<Recipe> findRecipesByTitleContainingOrDescriptionContainingOrderByTitleAsc (String search1, String search2);


}
