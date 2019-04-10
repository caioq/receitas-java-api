package challenge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe para mapear a receita no MongoDB
 *
 */

@Document
public class Recipe {

    @Id
    private String id;

    private String title;

    private String description;

    private List<String> likes = new ArrayList<>();

    private List<String> ingredients = new ArrayList<>();

    private List<RecipeComment> comments = new ArrayList<>();

    Recipe(){

    }

    public Recipe(String title, String description, List<String> likes, List<String> ingredients, List<RecipeComment> comments) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.ingredients = ingredients;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLikes() {
        return likes;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<RecipeComment> getComments() {
        return comments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setComments(List<RecipeComment> comments) {
        this.comments = comments;
    }

    public Boolean hasWordInDescriptionOrTittle(String word) {
        word = word.toLowerCase();
        return title.toLowerCase().contains(word) || description.toLowerCase().contains(word);
    }

    public Boolean hasIngredientInRecipe(String ingredient) {
        ingredient = ingredient.toLowerCase();
        return ingredients.contains(ingredient);
    }
}
