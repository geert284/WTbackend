package nl.workingtalent.backend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.service.BookService;

@CrossOrigin
@RestController
public class BookController {
	
	@Autowired
	private BookService service;
	
	@RequestMapping("book/all")
	public List<Book> getBooks() {
		return service.findAllBooks();
	}
	
	
	
	

	
	
}
