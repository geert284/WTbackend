package nl.workingtalent.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.workingtalent.backend.entity.BookCopy;
import nl.workingtalent.backend.repository.IBookCopyRepository;

@Service
public class BookCopyService {
	
	@Autowired
	private IBookCopyRepository repository;
	
	public List<BookCopy> findAllBookCopys() {
		return repository.findAll();
	}
}