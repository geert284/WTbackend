package nl.workingtalent.backend.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.dto.BookCopyDto;
import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.service.BookCopyService;

@CrossOrigin
@RestController
public class BookCopyController {
	
	@Autowired
	private BookCopyService service;
	
	@RequestMapping("bookcopy/all")
	public List<BookCopyDto> getBookCopys(){
		List<BookCopy> bookcopys = service.findAllBookCopys();
		List<BookCopyDto> dtos = new ArrayList<>();
		
		bookcopys.forEach(bookcopy -> {
			BookCopyDto bookcopyDto = new BookCopyDto();
			bookcopyDto.setStatus(bookcopy.getStatus());
			bookcopyDto.setAvailable(bookcopy.isAvailable());
			bookcopyDto.setBookId(bookcopy.getBook().getId());
			
			dtos.add(bookcopyDto);
		});
		
		return dtos;
	}

}
