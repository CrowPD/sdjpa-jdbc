package guru.springframework.sdjpa_jdbc.dao.mapper;

import guru.springframework.sdjpa_jdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setId(rs.getLong("id"));
		book.setTitle(rs.getString("title"));
		book.setPublisher(rs.getString("publisher"));
		book.setIsbn(rs.getString("isbn"));
		book.setAuthorId(rs.getLong("author_id"));
		return book;
	}
}
