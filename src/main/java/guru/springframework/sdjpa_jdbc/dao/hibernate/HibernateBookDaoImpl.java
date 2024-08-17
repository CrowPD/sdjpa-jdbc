package guru.springframework.sdjpa_jdbc.dao.hibernate;

import guru.springframework.sdjpa_jdbc.dao.BookDao;
import guru.springframework.sdjpa_jdbc.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class HibernateBookDaoImpl implements BookDao {
	@Override
	public Book getById(Long id) {
		return null;
	}

	@Override
	public Book findByTitle(String title) {
		return null;
	}

	@Override
	public Book save(Book book) {
		return null;
	}

	@Override
	public Book update(Book book) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}
}
