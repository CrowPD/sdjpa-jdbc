package guru.springframework.sdjpa_jdbc.dao.template;

import guru.springframework.sdjpa_jdbc.dao.AuthorDao;
import guru.springframework.sdjpa_jdbc.dao.mapper.AuthorMapper;
import guru.springframework.sdjpa_jdbc.domain.Author;
import org.springframework.dao.EmptyResultDataAccessException;
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
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", getMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
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
		jdbcTemplate.update("UPDATE author SET first_name = ?, last_name = ? WHERE id = ?", author.getFirstName(), author.getLastName(), author.getId());
		return getById(author.getId());
	}

	@Override
	public void deleteById(Long id) {
		jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
	}

	private RowMapper<Author> getMapper() {
		return new AuthorMapper();
	}
}
