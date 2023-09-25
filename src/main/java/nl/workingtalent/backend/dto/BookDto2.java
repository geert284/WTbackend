package nl.workingtalent.backend.dto;

public class BookDto2 {

	private long id;

	private String title;

	private int edition;

	private String ISBN;

	private String author;

	private String category;

	private String format;

	private String language;

	private boolean outOfUse;

	private long bookCopies;
	
	public long getBookCopies() {
		return bookCopies;
	}

	public void setBookCopies(long bookCopys) {
		this.bookCopies = bookCopys;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isOutOfUse() {
		return outOfUse;
	}

	public void setOutOfUse(boolean outOfUse) {
		this.outOfUse = outOfUse;
	}

}
