package guru.springframework.sdjpa_jdbc.dao.hibernate;

import guru.springframework.sdjpa_jdbc.dao.AuthorDao;
import guru.springframework.sdjpa_jdbc.domain.Author;
import org.springframework.stereotype.Component;

@Component
public class HiberAuthorDaoImpl implements AuthorDao {
	@Override
	public Author getById(Long id) {
		return null;
	}

	@Override
	public Author findByName(String firstName, String lastName) {
		return null;
	}

	@Override
	public Author save(Author author) {
		return null;
	}

	@Override
	public Author update(Author author) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}
}
