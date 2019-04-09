package challenge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends RuntimeException{


    private static final String RECIPE_NOT_FOUND_MESSAGE = "A receita não foi encontrado.";

    public RecipeNotFoundException() {
        super(RECIPE_NOT_FOUND_MESSAGE);
    }

}
