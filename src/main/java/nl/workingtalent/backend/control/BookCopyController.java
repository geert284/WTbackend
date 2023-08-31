package nl.workingtalent.backend.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.service.BookCopyService;

@CrossOrigin
@RestController
public class BookCopyController {
	
	@Autowired
	private BookCopyService service;

}
