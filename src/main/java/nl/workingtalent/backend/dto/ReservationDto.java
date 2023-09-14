package nl.workingtalent.backend.dto;

import java.time.LocalDateTime;

public class ReservationDto {

	private long id;

	private LocalDateTime reservationDate;

	private boolean processed;

	private long account_id;

	private long bookCopy_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDateTime reservationDate) {
		this.reservationDate = reservationDate;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public long getBookCopy_id() {
		return bookCopy_id;
	}

	public void setBookCopy_id(long bookCopy_id) {
		this.bookCopy_id = bookCopy_id;
	}

}
