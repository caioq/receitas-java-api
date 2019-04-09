package challenge;

import org.springframework.data.annotation.Id;

/**
 * Classe para mapear o comentario da receita no MongoDB
 *
 */
public class RecipeComment {

    @Id
    private String id;

    private String comment;


    public RecipeComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
