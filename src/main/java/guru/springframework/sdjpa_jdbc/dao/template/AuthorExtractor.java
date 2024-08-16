package guru.springframework.sdjpa_jdbc.dao.template;


import guru.springframework.sdjpa_jdbc.dao.template.mapper.AuthorMapper;
import guru.springframework.sdjpa_jdbc.domain.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorExtractor implements ResultSetExtractor<Author> {
	@Override
	public Author extractData(ResultSet rs) throws SQLException, DataAccessException {
		if (!rs.next()) {
			return null;
		}

		return new AuthorMapper().mapRow(rs, 0);
	}
}


