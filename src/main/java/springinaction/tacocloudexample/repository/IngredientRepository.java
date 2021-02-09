package springinaction.tacocloudexample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import springinaction.tacocloudexample.objects.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * Utilize Jdbc
 */

@Repository
public class IngredientRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public IngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    public Iterable<Ingredient> findAll() {
        return jdbc.query(
                "select id, name, type from Ingredient",
                this::mapRowToIngredient);
    }

    public Ingredient findOne(String id) {
        return jdbc.queryForObject(
                "select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient,
                id);
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
