package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.domain.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameIsContainingIgnoreCaseOrderByDateDesc(String name);
}
