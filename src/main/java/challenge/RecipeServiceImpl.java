package challenge;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

//	@Autowired
//	private MongoTemplate template;


	@Override
	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	@Override
	public void update(String id, Recipe recipe) {
		Recipe recipeUpdated = get(id);
		recipeUpdated.setTitle(recipe.getTitle());
		recipeUpdated.setDescription(recipe.getDescription());
		recipeUpdated.setIngredients(recipe.getIngredients());
		recipeRepository.save(recipeUpdated);
	}

	@Override
	public void delete(String id) {
		Recipe recipe = this.get(id);
		recipeRepository.delete(recipe);
	}

	@Override
	public Recipe get(String id) {
		return recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
	}

	@Override
	public List<Recipe> listByIngredient(String ingredient) {
		List<Recipe> recipes = recipeRepository.findRecipesByIngredientsOrderByTitleAsc(ingredient);
		return (recipes.size() > 0 ? recipes : null);
	}

	@Override
	public List<Recipe> search(String search) {

		List<Recipe> recipes = recipeRepository.findAll();
		List<Recipe> recipesFiltered = recipes.stream().
				map(r -> {
					r.setTitle(r.getTitle().toLowerCase());
					r.setDescription(r.getDescription().toLowerCase());
					return r;
				}).
				filter(r -> r.getTitle().contains(search.toLowerCase()) || r.getTitle().contains(search.toLowerCase())).
				sorted(Comparator.comparing(Recipe::getTitle)).collect(Collectors.toList());
		if(recipesFiltered.size() > 0){
			return recipesFiltered;
		}
		return null;
//		search = search.toLowerCase();
//		return recipeRepository.findRecipesByTitleContainingOrDescriptionContainingOrderByTitleAsc(search, search);
	}

	@Override
	public void like(String id, String userId) {
		Recipe recipe = this.get(id);
		List<String> likes = recipe.getLikes();
		// verifica se ja existe like deste usuario
		if(!likes.contains(userId)){
			likes.add(userId);
			recipe.setLikes(likes);
			this.save(recipe);
		}
	}

	@Override
	public void unlike(String id, String userId) {
		Recipe recipe = this.get(id);
		List<String> likes = recipe.getLikes();
		if(likes.contains(userId)){
			likes.remove(userId);
			recipe.setLikes(likes);
			this.save(recipe);
		}
	}

	@Override
	public RecipeComment addComment(String id, RecipeComment comment) {
		ObjectId idRecipeComment = new ObjectId();
		comment.setId(idRecipeComment.toString());
		Recipe recipe = this.get(id);
		List<RecipeComment> comments = recipe.getComments();
		comments.add(comment);
		recipe.setComments(comments);
		this.save(recipe);
		return comment;
	}

	@Override
	public void updateComment(String id, String commentId, RecipeComment comment) {
		Recipe recipe = get(id);
		List<RecipeComment> comments = recipe.getComments();

		List<RecipeComment> commentsUpdated = comments.stream().map(com -> {
			if(com.getId().equals(commentId)){
				com.setComment(comment.getComment());
			}
			return com;
			}).collect(Collectors.toList());

		// atualiza comentarios na receita
		recipe.setComments(commentsUpdated);
		recipeRepository.save(recipe);
	}

	@Override
	public void deleteComment(String id, String commentId) {
		Recipe recipe = get(id);
		List<RecipeComment> comments = recipe.getComments();

		List<RecipeComment> commentsUpdated = comments.stream().
				filter(com -> !com.getId().equals(commentId)).
				collect(Collectors.toList());

		// atualiza comentarios na receita
		recipe.setComments(commentsUpdated);
		recipeRepository.save(recipe);
	}

}
