package guru.springframework.sdjpa_jdbc.dao.template.mapper;

import guru.springframework.sdjpa_jdbc.domain.Author;
import guru.springframework.sdjpa_jdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorMapper implements RowMapper<Author> {
	@Override
	public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
		Author author = new Author();
		author.setId(rs.getLong("id"));
		author.setFirstName(rs.getString("first_name"));
		author.setLastName(rs.getString("last_name"));

		author.setBooks(new ArrayList<>());
		if (rs.getString("isbn") != null) {
			do {
				author.getBooks().add(mapBook(rs));
			} while (rs.next() && rs.getLong("id") == author.getId());
		}
		return author;
	}

	private Book mapBook(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setId(rs.getLong("book_id"));
		book.setTitle(rs.getString("title"));
		book.setPublisher(rs.getString("publisher"));
		book.setIsbn(rs.getString("isbn"));
		book.setAuthorId(rs.getLong("id"));
		return book;
	}
}
