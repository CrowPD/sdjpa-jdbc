package guru.springframework.sdjpa_jdbc.dao.hibernate;

import guru.springframework.sdjpa_jdbc.dao.BookDao;
import guru.springframework.sdjpa_jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class HibernateBookDaoImpl implements BookDao {
	private final EntityManagerFactory emf;

	public HibernateBookDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Book getById(Long id) {
		return getEntityManager().find(Book.class, id);
	}

	@Override
	public Book findByTitle(String title) {
		TypedQuery<Book> q = getEntityManager().createQuery(
				"SELECT b FROM Book b WHERE b.title = :title",
				Book.class
		);
		q.setParameter("title", title);
		return q.getSingleResult();
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

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
