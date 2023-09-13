package nl.workingtalent.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import nl.workingtalent.backend.entity.Book;

public interface IBookRepository extends JpaRepository<Book, Long>{



}
