package nl.workingtalent.backend.dto;

import java.util.Date;

public class AwaitingReservationDto {

	private long id;

	private Date requestDate;

	private boolean processed;

	private long book_id;

	private long account_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public long getBook_id() {
		return book_id;
	}

	public void setBook_id(long book_id) {
		this.book_id = book_id;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

}
