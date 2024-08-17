package guru.springframework.sdjpa_jdbc.dao.hibernate;

import guru.springframework.sdjpa_jdbc.dao.AuthorDao;
import guru.springframework.sdjpa_jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class HiberAuthorDaoImpl implements AuthorDao {
	private final EntityManagerFactory emf;

	public HiberAuthorDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Author getById(Long id) {
		return getEntityManager().find(Author.class, id);
	}

	@Override
	public Author findByName(String firstName, String lastName) {
		TypedQuery<Author> q = getEntityManager().createQuery(
				"SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName",
				Author.class
		);
		q.setParameter("firstName", firstName);
		q.setParameter("lastName", lastName);
		return q.getSingleResult();
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

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
