package pl.spring.demo.service;

import pl.spring.demo.criteria.BookSearchCriteria;
import pl.spring.demo.to.BookTo;

import java.util.List;

public interface BookService {

	List<BookTo> findAllBooks();

	List<BookTo> findBooksByTitle(String title);

	List<BookTo> findBooksByAuthor(String author);

	List<BookTo> findBookByCriteria(BookSearchCriteria bookSearchCriteria);

	BookTo saveBook(BookTo book);
}
