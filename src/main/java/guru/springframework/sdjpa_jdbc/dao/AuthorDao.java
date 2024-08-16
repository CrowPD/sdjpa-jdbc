package guru.springframework.sdjpa_jdbc.dao;

import guru.springframework.sdjpa_jdbc.domain.Author;
import org.springframework.stereotype.Component;

@Component
public interface AuthorDao {
	Author getById(Long id);

	Author findByName(String firstName, String lastName);

	Author save(Author author);

	Author update(Author author);

	void delete(Author author);
}
