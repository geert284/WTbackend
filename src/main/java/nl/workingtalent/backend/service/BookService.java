package nl.workingtalent.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.repository.IBookRepository;

@Service
public class BookService {
	
	@Autowired
	private IBookRepository repository;
	
	public List<Book> findAllBooks() {
		return repository.findAll();
	}

	
	public void create(Book book) {
		repository.save(book);
	}



}
