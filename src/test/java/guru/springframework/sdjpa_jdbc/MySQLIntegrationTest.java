package guru.springframework.sdjpa_jdbc;

import guru.springframework.sdjpa_jdbc.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan("guru.springframework.sdpa_jdbc.repositories")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {
	@Autowired
	BookRepository bookRepository;
	@Test
	void testMySQLConnection() {
		assertThat(bookRepository.count()).isGreaterThan(0);
	}
}
