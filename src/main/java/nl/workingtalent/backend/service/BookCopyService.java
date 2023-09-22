package nl.workingtalent.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.repository.IBookCopyRepository;

@Service
public class BookCopyService {
	
	@Autowired
	private IBookCopyRepository repository;
	
	public List<BookCopy> findAllBookCopys() {
		return repository.findAll();
	}
	
	public Optional<BookCopy> findFirstAvailableBookCopy(Book book) {
		return repository.findFirstByBookAndAvailableIsTrue(book);
	}
	
	public void update(BookCopy dbBookCopy) {
		repository.save(dbBookCopy);
  }
  
	public Optional<BookCopy> findById(long id){
		return repository.findById(id);
	}

	public void create(BookCopy bookcopy) {
		repository.save(bookcopy);
		
	}

	public void deleteBookCopy(long id) {
		repository.deleteById(id);
		
	}
	
	public Optional<BookCopy> findHighestBookCopyByTagNumberDesc(long bookId){
		return repository.findFirstByBookIdOrderByTagNumberDesc(bookId);
	}
	
}
