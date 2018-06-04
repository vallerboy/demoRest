package pl.oskarpolak.demo.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.oskarpolak.demo.model.BookEntity;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Integer> {
    boolean existsByTitle(String title);
    boolean existsById(int id);
}
