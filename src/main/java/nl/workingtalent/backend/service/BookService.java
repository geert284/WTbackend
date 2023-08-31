package nl.workingtalent.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.repository.IBookRepository;

@Service
public class BookService {
	
	@Autowired
	private IBookRepository repository;
	
	

}
