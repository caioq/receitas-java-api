package challenge;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
		//Optional<Recipe> oldRecipe = recipeRepository.findById(id);
		//recipe.setId(id);
		recipeRepository.save(recipe);
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
		List<Recipe> recipes = recipeRepository.findAll();
		recipes = recipes.stream().filter(r -> {
			//List<String> listIngredients = r.getIngredients();
			System.out.println("Ingredientes: " + r.getIngredients().toString() + r.hasIngredientInRecipe(ingredient));
			return r.hasIngredientInRecipe(ingredient);
		}).sorted(Comparator.comparing(Recipe::getTitle)).collect(Collectors.toList());
		if(!recipes.isEmpty()){
			return recipes;
		}
		return null;
	}

	@Override
	public List<Recipe> search(String search) {
		List<Recipe> recipes = recipeRepository.findAll();
		recipes = recipes.stream().filter(r -> {
			//System.out.println(listIngredients);
			return r.hasWordInDescriptionOrTittle(search);
		}).sorted(Comparator.comparing(Recipe::getTitle)).collect(Collectors.toList());
		if(!recipes.isEmpty()){
			return recipes;
		}
		return null;
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
		//RecipeComment recipeComment = new RecipeComment(comment.getComment());
//		Recipe recipe = this.get(id);
//		List<RecipeComment> comments = recipe.getComments();
//		comments.add(comment);
//		recipe.setComments(comments);
//		this.save(recipe);
//		return comment;
		return null;
	}

	@Override
	public void updateComment(String id, String commentId, RecipeComment comment) {

	}

	@Override
	public void deleteComment(String id, String commentId) {

	}

}
