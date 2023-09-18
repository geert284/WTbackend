package nl.workingtalent.backend.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(length = 3, nullable = false)
	private int edition;

	@Column(length = 50, nullable = false)
	private String ISBN;

	@Column(length = 100, nullable = false)
	private String author;

	@Column(length = 50, nullable = false)
	private String category;

	@Column(length = 50, nullable = false)
	private String format;

	@Column(length = 50, nullable = false)
	private String language;

	@Column(nullable = false)
	private boolean outOfUse;

	@JsonIgnore
	@OneToMany(mappedBy = "book", orphanRemoval = true, cascade = { CascadeType.REMOVE })
	private List<BookCopy> copies;

	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<AwaitingReservation> awaitingReservations;

	public List<AwaitingReservation> getAwaitingReservations() {
		return awaitingReservations;
	}

	public void setAwaitingReservations(List<AwaitingReservation> awaitingReservations) {
		this.awaitingReservations = awaitingReservations;
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

	public List<BookCopy> getCopies() {
		return copies;
	}

	public void setCopies(List<BookCopy> copies) {
		this.copies = copies;
	}

	public boolean isOutOfUse() {
		return outOfUse;
	}

	public void setOutOfUse(boolean outOfUse) {
		this.outOfUse = outOfUse;
	}

}
