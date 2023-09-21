package nl.workingtalent.backend.dto;

import jakarta.persistence.Column;
import nl.workingtalent.backend.entity.Book;

public class BookCopyDto {
	

	private long id;
	
	private boolean available;

	private String status;

	private long bookId;
	
	private boolean outOfUse;
	
	@Column(length = 50)
	private int tagNumber;
	
	public int getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(int tagNumber) {
		this.tagNumber = tagNumber;
	}

	public boolean isOutOfUse() {
		return outOfUse;
	}

	public void setOutOfUse(boolean outOfUse) {
		this.outOfUse = outOfUse;
	}

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
