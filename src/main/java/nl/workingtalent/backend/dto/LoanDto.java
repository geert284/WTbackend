package nl.workingtalent.backend.dto;

import java.time.LocalDateTime;

public class LoanDto {

	private long id;

	private LocalDateTime loanDate;

	private LocalDateTime returnDate;

	private long bookCopy_id;

	private long account_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDateTime loanDate) {
		this.loanDate = loanDate;
	}

	public LocalDateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}

	public long getBookCopy_id() {
		return bookCopy_id;
	}

	public void setBookCopy_id(long bookCopy_id) {
		this.bookCopy_id = bookCopy_id;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

}
