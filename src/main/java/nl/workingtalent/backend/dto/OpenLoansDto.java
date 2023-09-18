package nl.workingtalent.backend.dto;

import java.time.LocalDateTime;

public class OpenLoansDto {

	private LocalDateTime loanDate;
	
	private String title;
	
	private int tagNumber;
	
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

	public int getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(int tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name; // person who loaned the book
	
}
