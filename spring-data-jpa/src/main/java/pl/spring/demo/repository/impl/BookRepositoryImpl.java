package pl.spring.demo.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jinq.jpa.JPAJinqStream;
import org.jinq.jpa.JPQL;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysema.query.jpa.impl.JPAQuery;

import pl.spring.demo.criteria.BookSearchCriteria;
import pl.spring.demo.entity.AuthorEntity;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.entity.QBookEntity;
import pl.spring.demo.repository.BookRepositoryJinq;
import pl.spring.demo.repository.BookRepositoryQueryDsl;
import pl.spring.demo.repository.predicate.BookSearchPredicate;

public class BookRepositoryImpl implements BookRepositoryQueryDsl, BookRepositoryJinq {

	@PersistenceContext(name = "hsql")
	private EntityManager entityManager;
	@Autowired
	private JinqJPAStreamProvider jinqJPAStreamProvider;

	@Override
	public List<BookEntity> findBooksBySearchCriteriaQueryDsl(BookSearchCriteria bookSearchCriteria) {
		QBookEntity qBookEntity = QBookEntity.bookEntity;

		return new JPAQuery(entityManager)
				.from(qBookEntity)
				.where(BookSearchPredicate.getBookSearchPredicate(bookSearchCriteria))
				.list(qBookEntity);
	}

	@Override
	public List<BookEntity> findBooksBySearchCriteriaJinq(BookSearchCriteria bookSearchCriteria) {
		JPAJinqStream<BookEntity> books = jinqJPAStreamProvider.streamAll(entityManager, BookEntity.class);

		if (bookSearchCriteria.getTitle() != null) {
			String title = bookSearchCriteria.getTitle().toLowerCase();
			books = books.where(book -> book.getTitle().toLowerCase().contains(title));
		}

		if (bookSearchCriteria.getAuthor() != null) {
			String authorName = bookSearchCriteria.getAuthor().toLowerCase();
			books = books.where((book, authors) -> JPQL.isIn(
					authorName,
					authors.stream(AuthorEntity.class)
						.where(author -> book.getAuthors().contains(author))
						.where(author -> (author.getFirstName() + " " + author.getLastName()).toLowerCase().contains(authorName))
						.select(author -> (author.getFirstName() + " " + author.getLastName()).toLowerCase())
					));
		}

		if (bookSearchCriteria.getLibraryName() != null) {
			String libraryName = bookSearchCriteria.getLibraryName().toLowerCase();
			books = books.where(book -> book.getLibrary().getName().toLowerCase().contains(libraryName));
		}

		return books.toList();
	}
}
