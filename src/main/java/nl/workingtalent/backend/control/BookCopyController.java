package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookCopyDto;
import nl.workingtalent.backend.dto.BookCopyUpdateDto;
import nl.workingtalent.backend.dto.BookDto;
import nl.workingtalent.backend.dto.BookUpdateDto;
import nl.workingtalent.backend.entity.Book;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.service.BookCopyService;
import nl.workingtalent.backend.service.BookService;

@CrossOrigin
@RestController
public class BookCopyController {

	@Autowired
	private BookCopyService service;

	@Autowired
	private BookService bookService;

	@RequestMapping("bookcopy/all")
	public List<BookCopyDto> getBookCopys() {
		List<BookCopy> bookcopys = service.findAllBookCopys();
		List<BookCopyDto> dtos = new ArrayList<>();

		bookcopys.forEach(bookcopy -> {
			BookCopyDto bookcopyDto = new BookCopyDto();
			bookcopyDto.setStatus(bookcopy.getStatus());
			bookcopyDto.setAvailable(bookcopy.isAvailable());
			bookcopyDto.setBookId(bookcopy.getBook().getId());
			bookcopyDto.setId(bookcopy.getId());
			bookcopyDto.setOutOfUse(bookcopy.isOutOfUse());

			dtos.add(bookcopyDto);
		});

		return dtos;
	}

	@RequestMapping("bookcopy/{id}")
	public Optional<BookCopy> findBookCopy(@PathVariable long id) {
		return service.findById(id);
	}

	@RequestMapping(value = "bookcopy/create", method = RequestMethod.POST)
	// @PostMapping("book/create")
	public void createBook(@RequestBody BookCopyDto bookcopyDto) {
		// SavebookDTo controller toevoegen
		BookCopy bookcopy = new BookCopy();

		bookcopy.setStatus(bookcopyDto.getStatus());
		bookcopy.setAvailable(bookcopyDto.isAvailable());
		bookcopy.setOutOfUse(bookcopyDto.isOutOfUse());
		Optional<Book> opBook = bookService.findById(bookcopyDto.getBookId());
		if (opBook.isEmpty()) {
			return;
		}
		bookcopy.setBook(opBook.get());

		service.create(bookcopy);
	}

	// It also needs to check if it has a child row
	@PostMapping("bookcopy/delete/{id}")
	public void deleteBook(@PathVariable long id) {

		service.deleteBookCopy(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "bookcopy/update/{id}")
	public void updateBook(@PathVariable long id, @RequestBody BookCopyUpdateDto bookCopyUpdateDto) {
		Optional<BookCopy> optional = service.findById(id);
		if (optional.isEmpty()) {
			return;
		}
		BookCopy dbBookCopy = optional.get();

		dbBookCopy.setStatus(bookCopyUpdateDto.getStatus());
		dbBookCopy.setAvailable(bookCopyUpdateDto.isAvailable());
		dbBookCopy.setOutOfUse(bookCopyUpdateDto.isOutOfUse());

		service.update(dbBookCopy);

	}

}
