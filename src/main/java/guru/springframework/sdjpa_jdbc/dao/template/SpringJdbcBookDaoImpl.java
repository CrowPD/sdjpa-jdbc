package guru.springframework.sdjpa_jdbc.dao.template;

import guru.springframework.sdjpa_jdbc.dao.BookDao;
import guru.springframework.sdjpa_jdbc.dao.template.mapper.BookMapper;
import guru.springframework.sdjpa_jdbc.domain.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringJdbcBookDaoImpl implements BookDao {
	private final JdbcTemplate jdbcTemplate;

	public SpringJdbcBookDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Book> findAll() {
		return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
	}

	@Override
	public Book getById(Long id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", getRowMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Book findByTitle(String title) {
		return jdbcTemplate.queryForObject("SELECT * FROM book WHERE title = ?", getRowMapper(), title);

	}

	@Override
	public Book save(Book book) {
		jdbcTemplate.update(
				"INSERT INTO book (title, publisher, isbn, author_id) VALUES (?, ?, ?, ?)",
				book.getTitle(),
				book.getPublisher(),
				book.getIsbn(),
				book.getAuthorId()
		);
		Long lastId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
		return getById(lastId);
	}

	@Override
	public Book update(Book book) {
		jdbcTemplate.update(
				"UPDATE book SET title = ?, publisher = ?, isbn = ?, author_id = ? WHERE id = ?",
				book.getTitle(),
				book.getPublisher(),
				book.getIsbn(),
				book.getAuthorId(),
				book.getId()
		);
		return getById(book.getId());
	}

	@Override
	public void deleteById(Long id) {
		jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
	}

	@Override
	public Book findByIsbn(String isbn) {
		return jdbcTemplate.queryForObject("SELECT * FROM book WHERE isbn = ?", getRowMapper(), isbn);
	}

	private RowMapper<Book> getRowMapper() {
		return new BookMapper();
	}
}
