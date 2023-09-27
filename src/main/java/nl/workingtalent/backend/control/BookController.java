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

import jakarta.servlet.http.HttpServletRequest;
import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.dto.BookUpdateDto;
import nl.workingtalent.backend.dto.DeleteBookDto;
import nl.workingtalent.backend.dto.SaveBookDto;
import nl.workingtalent.backend.entity.Account;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.service.AccountService;
import nl.workingtalent.backend.service.BookCopyService;
import nl.workingtalent.backend.service.BookService;

@CrossOrigin
@RestController
public class BookController {

	@Autowired
	private BookService service;
	
	@Autowired
	private BookCopyService bookCopyService;
	
	@Autowired
	private AccountService accountService;

	@RequestMapping("book/all")
	public List<BookDto> getBooks() {
		List<Book> books = service.findAllBooks();

		List<BookDto> dtos = new ArrayList<>();
		

		books.forEach(book -> {
			BookDto bookDto = new BookDto();
			bookDto.setBookCopies(bookCopyService.countByBookId(book.getId()));
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
	
	@RequestMapping("book/library")
	public List<BookDto> getBooks2() {
		List<Book> books = service.findAllBooks();

		List<BookDto> dtos = new ArrayList<>();

		books.forEach(book -> {
			BookDto bookDto = new BookDto();
			bookDto.setBookCopies(bookCopyService.countByBookIdAvailable(book.getId()));
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
	public void createBook(HttpServletRequest request, @RequestBody BookDto bookDto) {
		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header meegegeven");
			return;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account thisAccount = optional.get();
		// end authentication part
		if (!thisAccount.isAdmin()) {
			System.out.println("Account is geen admin");
			return;
		}
		// end authentication part with admin check
				
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
	public void updateBook(HttpServletRequest request, @PathVariable long id, @RequestBody BookUpdateDto bookUpdateDto) {
		// authentication part
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isBlank()) {
			System.out.println("Geen header meegegeven");
			return;
		}
		Optional<Account> optional = accountService.findByToken(authHeader);
		if (optional.isEmpty()) {
			System.out.println("Account niet gevonden");
			return;
		}
		System.out.println("Account is gevonden met naam " + optional.get().getEmail());
		Account thisAccount = optional.get();
		// end authentication part
		if (!thisAccount.isAdmin()) {
			System.out.println("Account is geen admin");
			return;
		}
		// end authentication part with admin check
				
		Optional<Book> optionalBook = service.findById(id);
		if (optionalBook.isEmpty()) {
			return;
		}
		Book dbBook = optionalBook.get();

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
