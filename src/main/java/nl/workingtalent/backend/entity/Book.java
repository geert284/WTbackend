package nl.workingtalent.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Column(length = 3, nullable = false)
	private int edition;
	
	@Column(length = 50, nullable = false)
	private String ISBN;
	
	@Column(length = 100, nullable = false)
	private String author;
	
	@Column(length = 50, nullable = false)
	private String category;
	
	@Column(length = 50, nullable = false)
	private String collation;
	
	@Column(length = 50, nullable = false)
	private String language;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
