package nl.workingtalent.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.workingtalent.backend.entity.BookCopy;

public interface IBookCopyRepository extends JpaRepository<BookCopy, Long>{

}
