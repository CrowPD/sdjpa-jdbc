package guru.springframework.sdjpa_jdbc.dao;

import guru.springframework.sdjpa_jdbc.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan("guru.springframework.sdjpa_jdbc.dao.hibernate")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoTest {
	@Autowired
	BookDao bookDao;

	@Test
	void testGetById() {
		assertThat(bookDao.getById(1L)).isNotNull();
	}

	@Test
	void testFindByTitle() {
		Book byId = bookDao.getById(1L);

		Book byTitle = bookDao.findByTitle(byId.getTitle());
		assertThat(byTitle).isNotNull();
		assertThat(byTitle.getId()).isEqualTo(byId.getId());
	}

	@Test
	void testSave() {
		Book book = new Book();
		book.setTitle("New Testing Amendment");
		book.setIsbn("333-555-101");
		book.setPublisher("Tat Media");
		book.setAuthorId(1L);
		var saved = bookDao.save(book);

		assertThat(saved.getId()).isGreaterThan(0);

		bookDao.deleteById(saved.getId());
	}

	@Test
	void testUpdate() {
		Book book = new Book();
		book.setTitle("New Testing Amendment");
		book.setIsbn("333-555-101");
		book.setPublisher("Tat Media");
		book.setAuthorId(1L);
		var saved = bookDao.save(book);


		saved.setPublisher("Faf Media");
		Book updated = bookDao.update(saved);

		assertThat(updated.getPublisher()).isEqualTo(saved.getPublisher());

		bookDao.deleteById(saved.getId());
	}

	@Test
	void testDelete() {
		Book book = new Book();
		book.setTitle("New Testing Amendment");
		book.setIsbn("333-555-101");
		book.setPublisher("Tat Media");
		book.setAuthorId(1L);
		var saved = bookDao.save(book);

		bookDao.deleteById(saved.getId());

		assertThat(bookDao.getById(saved.getId())).isNull();
	}

	@Test
	void testFindByIsbn() {
		assertThat(bookDao.findByIsbn("978-1617297571")).isNotNull();
	}
}
