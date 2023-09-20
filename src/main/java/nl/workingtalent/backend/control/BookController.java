package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.dto.BookUpdateDto;
import nl.workingtalent.backend.dto.DeleteBookDto;
import nl.workingtalent.backend.dto.SaveBookDto;
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
			bookDto.setTitle(book.getTitle());
			bookDto.setFormat(book.getFormat());
			bookDto.setISBN(book.getISBN());
			bookDto.setId(book.getId());
			bookDto.setLanguage(book.getLanguage());

			dtos.add(bookDto);
		});

		return dtos;
	}

	@RequestMapping("book/{id}")
	public Optional<Book> findBook(@PathVariable long id) {
		return service.findById(id);
	}

	@RequestMapping(value = "book/create", method = RequestMethod.POST)
	// @PostMapping("book/create")
	public void createBook(@RequestBody BookDto bookDto) {
		// SavebookDTo controller toevoegen
		Book book = new Book();
		book.setAuthor(bookDto.getAuthor());
		book.setCategory(bookDto.getCategory());
		book.setCategory(bookDto.getCategory());
		book.setEdition(bookDto.getEdition());
		book.setTitle(bookDto.getTitle());
		book.setFormat(bookDto.getFormat());
		book.setISBN(bookDto.getISBN());
		book.setLanguage(bookDto.getLanguage());
		book.setOutOfUse(false);

		service.create(book);
	}

	// It also needs to check if it has a child row
	@PostMapping("book/delete/{id}")
	public void deleteBook(@PathVariable long id) {


		service.deleteBook(id);
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "book/update/{id}")
	public void updateBook(@PathVariable long id, @RequestBody BookUpdateDto bookUpdateDto) {
		Optional<Book>optional = service.findById(id);
		if (optional.isEmpty()) {
			return;
		}
		Book dbBook = optional.get();
		
		dbBook.setAuthor(bookUpdateDto.getAuthor());
		dbBook.setCategory(bookUpdateDto.getCategory());
		dbBook.setCategory(bookUpdateDto.getCategory());
		dbBook.setEdition(bookUpdateDto.getEdition());
		dbBook.setTitle(bookUpdateDto.getTitle());
		dbBook.setFormat(bookUpdateDto.getFormat());
		dbBook.setISBN(bookUpdateDto.getISBN());
		dbBook.setLanguage(bookUpdateDto.getLanguage());
		dbBook.setOutOfUse(false);
		
		service.update(dbBook);
		
		
	}

}
