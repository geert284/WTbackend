package nl.workingtalent.backend.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.service.BookService;

@CrossOrigin
@RestController
public class BookController {
	
	@Autowired
	private BookService service;
	
	
	
	

	
	
}
