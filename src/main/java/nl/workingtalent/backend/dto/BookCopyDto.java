package nl.workingtalent.backend.dto;

import nl.workingtalent.backend.entity.Book;

public class BookCopyDto {
	

	private long id;
	
	private boolean available;

	private String status;

	private long bookId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	

}
