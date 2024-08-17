package guru.springframework.sdjpa_jdbc.dao.hibernate;

import guru.springframework.sdjpa_jdbc.dao.BookDao;
import guru.springframework.sdjpa_jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
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
		EntityManager em = getEntityManager();
		Book book = em.find(Book.class, id);
		em.close();
		return book;
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
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(book);
		em.flush();
		transaction.commit();
		em.close();
		return getById(book.getId());
	}

	@Override
	public Book update(Book book) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(book);
		em.flush();
		transaction.commit();
		em.close();
		return getById(book.getId());
	}

	@Override
	public void deleteById(Long id) {
		EntityManager em = getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.remove(em.find(Book.class, id));
		em.flush();
		transaction.commit();
		em.close();
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
