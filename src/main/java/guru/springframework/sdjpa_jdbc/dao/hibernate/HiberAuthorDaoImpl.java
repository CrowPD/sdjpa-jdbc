package guru.springframework.sdjpa_jdbc.dao.hibernate;

import guru.springframework.sdjpa_jdbc.dao.AuthorDao;
import guru.springframework.sdjpa_jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HiberAuthorDaoImpl implements AuthorDao {
	private final EntityManagerFactory emf;

	public HiberAuthorDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Author getById(Long id) {
		try (EntityManager em = getEntityManager()) {
			return em.find(Author.class, id);
		}
	}

	@Override
	public Author findByName(String firstName, String lastName) {
		try(EntityManager em = getEntityManager()) {
			TypedQuery<Author> q = em.createQuery(
					"SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName",
					Author.class
			);
			q.setParameter("firstName", firstName);
			q.setParameter("lastName", lastName);
			return q.getSingleResult();
		}
	}

	@Override
	public Author save(Author author) {
		try (EntityManager em = getEntityManager()) {
			em.getTransaction().begin();
			em.persist(author);
			em.flush();
			em.getTransaction().commit();
		}

		return getById(author.getId());
	}

	@Override
	public Author update(Author author) {
		try (EntityManager em = getEntityManager()) {
			em.getTransaction().begin();
			em.merge(author);
			em.flush();
			em.getTransaction().commit();
		}
		return getById(author.getId());
	}

	@Override
	public void deleteById(Long id) {
		try (EntityManager em = getEntityManager()) {
			em.getTransaction().begin();
			em.remove(em.find(Author.class, id));
			em.flush();
			em.getTransaction().commit();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Author> listAuthorsByLastName(String lastName) {
		try (EntityManager em = getEntityManager()) {
			Query q = em.createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :lastName");
			q.setParameter("lastName", String.format("%%%s%%", lastName));
			return q.getResultList();
		}
	}

	@Override
	public List<Author> findAll() {
		try (EntityManager em = getEntityManager()) {
			TypedQuery<Author> q = em.createNamedQuery("authorFindAll", Author.class);

			return q.getResultList();
		}
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
