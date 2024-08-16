package guru.springframework.sdjpa_jdbc.dao;

import guru.springframework.sdjpa_jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan("guru.springframework.sdjpa_jdbc.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoTest {
	@Autowired
	AuthorDao authorDao;

	@Test
	void testGetById() {
		Author author = authorDao.getById(1L);

		assertThat(author).isNotNull();
	}

	@Test
	void testGetByName() {
		Author foundByName = authorDao.findByName("Eric", "Evans");
		assertThat(foundByName).isNotNull();

		Author foundById = authorDao.getById(foundByName.getId());
		assertThat(foundById).isNotNull();
		assertThat(foundByName.getFirstName()).isEqualTo(foundById.getFirstName());
		assertThat(foundByName.getLastName()).isEqualTo(foundById.getLastName());
	}

	@Test
	void testSave() {
		Author author = new Author();
		author.setFirstName("Andy");
		author.setLastName("Newman");

		Author saved = authorDao.save(author);

		assertThat(saved.getFirstName()).isEqualTo(author.getFirstName());
	}

	@Test
	void testUpdate() {
		Author author = new Author();
		author.setFirstName("Andy");
		author.setLastName("Newman");

		Author saved = authorDao.save(author);

		saved.setLastName("Oldboy");
		authorDao.update(saved);

		Author read = authorDao.getById(saved.getId());

		assertThat(read.getLastName()).isEqualTo(saved.getLastName());
	}

	@Test
	void testDelete() {
		Author author = new Author();
		author.setFirstName("Andy");
		author.setLastName("Newman");

		Author saved = authorDao.save(author);

		authorDao.delete(saved);
		assertThat(authorDao.getById(saved.getId())).isNull();
	}
}
