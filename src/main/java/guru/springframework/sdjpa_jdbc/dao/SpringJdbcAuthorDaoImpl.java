package guru.springframework.sdjpa_jdbc.dao;

import guru.springframework.sdjpa_jdbc.dao.mapper.AuthorMapper;
import guru.springframework.sdjpa_jdbc.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SpringJdbcAuthorDaoImpl implements AuthorDao {
	private final JdbcTemplate jdbcTemplate;

	public SpringJdbcAuthorDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Author getById(Long id) {
		return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", getMapper(), id);
	}

	@Override
	public Author findByName(String firstName, String lastName) {
		return jdbcTemplate.queryForObject("SELECT * FROM author WHERE first_name = ? AND last_name = ?", getMapper(), firstName, lastName);
	}

	@Override
	public Author save(Author author) {
		jdbcTemplate.update("INSERT INTO author (first_name, last_name) VALUES (?, ?)", author.getFirstName(), author.getLastName());
		Long lastId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		return getById(lastId);
	}

	@Override
	public Author update(Author author) {
		return null;
	}

	@Override
	public void delete(Author author) {

	}

	private RowMapper<Author> getMapper() {
		return new AuthorMapper();
	}
}
