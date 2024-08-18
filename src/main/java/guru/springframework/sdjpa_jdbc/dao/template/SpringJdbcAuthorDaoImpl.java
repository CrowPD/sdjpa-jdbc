package guru.springframework.sdjpa_jdbc.dao.template;

import guru.springframework.sdjpa_jdbc.dao.AuthorDao;
import guru.springframework.sdjpa_jdbc.dao.template.mapper.AuthorMapper;
import guru.springframework.sdjpa_jdbc.domain.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringJdbcAuthorDaoImpl implements AuthorDao {
	private static final String SELECT_FULL_AUTHOR_INFO_BASE_QUERY = "SELECT a.*, b.id as book_id, title, isbn, publisher FROM author a LEFT JOIN book b ON b.author_id = a.id";

	private final JdbcTemplate jdbcTemplate;

	public SpringJdbcAuthorDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Author> findAll() {
		return jdbcTemplate.query(SELECT_FULL_AUTHOR_INFO_BASE_QUERY, new AuthorMapper());
	}

	@Override
	public Author getById(Long id) {
		try {
			return jdbcTemplate.query(
					SELECT_FULL_AUTHOR_INFO_BASE_QUERY + " WHERE a.id = ?",
					new AuthorExtractor(),
					id
			);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Author findByName(String firstName, String lastName) {
		return jdbcTemplate.query(
				SELECT_FULL_AUTHOR_INFO_BASE_QUERY + " WHERE first_name = ? AND last_name = ?",
				new AuthorExtractor(),
				firstName,
				lastName
		);
	}

	@Override
	public Author findByNameCriteria(String firstName, String lastName) {
		// a stub, we don't need criteria gere
		return findByName(firstName, lastName);
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

	@Override
	public List<Author> listAuthorsByLastName(String lastName) {
		return jdbcTemplate.query(
				SELECT_FULL_AUTHOR_INFO_BASE_QUERY + " WHERE a.last_name LIKE CONCAT('%', ?, '%')",
				new AuthorMapper(),
				lastName
		);
	}
}
