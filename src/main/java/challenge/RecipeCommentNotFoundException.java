package challenge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecipeCommentNotFoundException extends RuntimeException{

    private static final String RECIPE_COMMENT_NOT_FOUND_MESSAGE = "Comentário não foi encontrado.";

    public RecipeCommentNotFoundException() {
        super(RECIPE_COMMENT_NOT_FOUND_MESSAGE);
    }
}
