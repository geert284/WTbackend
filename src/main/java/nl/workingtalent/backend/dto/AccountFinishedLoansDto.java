package nl.workingtalent.backend.dto;

import java.time.LocalDateTime;

public class AccountFinishedLoansDto {

	private LocalDateTime loanDate;
	
	private LocalDateTime returnDate;

	private String title;

	private int tagNumber;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(int tagNumber) {
		this.tagNumber = tagNumber;
	}

}
