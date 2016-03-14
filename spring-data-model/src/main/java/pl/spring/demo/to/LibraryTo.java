package pl.spring.demo.to;

import java.util.HashSet;
import java.util.Set;

public class LibraryTo {

	private Long id;
	private String name;
	private Set<BookTo> books = new HashSet<>();

	public LibraryTo(Long id, String name, Set<BookTo> books) {
		this.id = id;
		this.name = name;
		this.books = books;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<BookTo> getBooks() {
		return books;
	}

	public void setBooks(Set<BookTo> books) {
		this.books = books;
	}
}
