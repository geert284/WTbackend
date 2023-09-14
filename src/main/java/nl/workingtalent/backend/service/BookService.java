package nl.workingtalent.backend.service;

import java.util.List;
import java.util.Optional;

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

	public Optional<Book> findById(long id){
		return repository.findById(id);
	}
	
	public void deleteBook(long id) {
		repository.deleteById(id);
		
	}

}
