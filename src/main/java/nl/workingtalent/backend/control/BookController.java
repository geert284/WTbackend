package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookAndBookCopyDto;
import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.service.BookService;

@CrossOrigin
@RestController
public class BookController {
	
	@Autowired
	private BookService service;
	
	@RequestMapping("book/all")
	public List<BookDto> getBooks() {
		List<Book> books = service.findAllBooks();
		
		List<BookDto> dtos = new ArrayList<>();
		
		books.forEach(book -> {
			BookDto bookDto = new BookDto();
			bookDto.setAuthor(book.getAuthor());
			bookDto.setCategory(book.getCategory());
			bookDto.setEdition(book.getEdition());
			
			dtos.add(bookDto);
		});
		
		return dtos;
	}
	
	@RequestMapping(value="book/create", method=RequestMethod.POST)
	//@PostMapping("book/create")
	public void createBook(@RequestBody BookDto bookDto) {
		// SavebookDTo controller toevoegen
		Book book = new Book();
		book.setAuthor(bookDto.getAuthor());
		book.setCategory(bookDto.getCategory());
		book.setCategory(bookDto.getCategory());
		book.setEdition(bookDto.getEdition());
		book.setTitle(bookDto.getTitle());
		book.setISBN(bookDto.getISBN());
		book.setLanguage(bookDto.getLanguage());
	}
	
	
	@DeleteMapping("book/{id}")
	public void delete(@PathVariable long id) {
		
	}

	@GetMapping("book/extra")
	public List<BookAndBookCopyDto> getMetBookCopy() {
		// Lijst van boeken uit de database
		
		// Voor elke boek maak een BookAndBookCopyDto
		
		return null;
	}
	
}
