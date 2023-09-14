package nl.workingtalent.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;

public interface IBookCopyRepository extends JpaRepository<BookCopy, Long>{

	Optional<BookCopy> findByBookAndAvailableIsTrue(Book book);
	
}
