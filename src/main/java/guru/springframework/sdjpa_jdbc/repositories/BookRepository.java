package guru.springframework.sdjpa_jdbc.repositories;

import guru.springframework.sdjpa_jdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
