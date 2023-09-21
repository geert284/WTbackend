package nl.workingtalent.backend.dto;

public class BookCopyUpdateDto {

	private long id;

	private boolean available;

	private String status;

	private long bookId;

	private boolean outOfUse;

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
