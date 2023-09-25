package nl.workingtalent.backend.dto;

import java.time.LocalDateTime;

public class AccountLoansDto {

	private LocalDateTime loanDate;

	private String title;

	private String tagNumber;

	public LocalDateTime getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDateTime loanDate) {
		this.loanDate = loanDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

}
