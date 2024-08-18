package guru.springframework.sdjpa_jdbc.dao.hibernate;

import guru.springframework.sdjpa_jdbc.dao.BookDao;
import guru.springframework.sdjpa_jdbc.domain.Book;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
public class HibernateBookDaoImpl implements BookDao {
	private final EntityManagerFactory emf;

	public HibernateBookDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Book getById(Long id) {
		try (EntityManager em = getEntityManager()) {
			return em.find(Book.class, id);
		}
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
		try (EntityManager em = getEntityManager()) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.persist(book);
			em.flush();
			transaction.commit();
		}
		return getById(book.getId());
	}

	@Override
	public Book update(Book book) {
		try (EntityManager em = getEntityManager()) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(book);
			em.flush();
			transaction.commit();
		}
		return getById(book.getId());
	}

	@Override
	public void deleteById(Long id) {
		try (EntityManager em = getEntityManager()) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(em.find(Book.class, id));
			em.flush();
			transaction.commit();
		}
	}

	@Override
	public Book findByIsbn(String isbn) {
		try(EntityManager em = getEntityManager()) {
			TypedQuery<Book> q = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
			q.setParameter("isbn", isbn);
			return q.getSingleResult();
		}
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
