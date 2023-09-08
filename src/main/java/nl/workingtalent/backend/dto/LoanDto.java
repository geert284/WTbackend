package nl.workingtalent.backend.dto;

import java.util.Date;

public class LoanDto {

	private long id;

	private Date loanDate;

	private Date returnDate;

	private long bookCopy_id;

	private long account_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
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
