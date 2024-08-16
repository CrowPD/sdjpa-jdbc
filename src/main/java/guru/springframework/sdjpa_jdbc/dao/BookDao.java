package guru.springframework.sdjpa_jdbc.dao;

import guru.springframework.sdjpa_jdbc.domain.Book;
import org.springframework.stereotype.Component;

@Component
public interface BookDao {
	Book getById(Long id);

	Book findByTitle(String title);

	Book save(Book book);

	Book update(Book book);

	void deleteById(Long id);
}
