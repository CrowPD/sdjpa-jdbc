package guru.springframework.sdjpa_jdbc.repositories;

import guru.springframework.sdjpa_jdbc.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
