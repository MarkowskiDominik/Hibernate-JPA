package pl.spring.demo.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

import pl.spring.demo.criteria.BookSearchCriteria;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.entity.QAuthorEntity;
import pl.spring.demo.entity.QBookEntity;
import pl.spring.demo.entity.QLibraryEntity;
import pl.spring.demo.repository.BookRepositoryCustom;

public class BookRepositoryImpl implements BookRepositoryCustom {

	@PersistenceContext(name = "hsql")
	private EntityManager entityManager;

	@Override
	public List<BookEntity> findBooksBySearchCriteria(BookSearchCriteria bookSearchCriteria) {
		QBookEntity qBookEntity = QBookEntity.bookEntity;
		QAuthorEntity qAuthorEntity = QAuthorEntity.authorEntity;
		QLibraryEntity qLibraryEntity = QLibraryEntity.libraryEntity;
		
		JPAQuery jpaQuery = new JPAQuery(entityManager).from(qBookEntity);
		BooleanBuilder restrictions = new BooleanBuilder();

		if (bookSearchCriteria.getTitle() != null) {
			restrictions.and(qBookEntity.title.startsWithIgnoreCase(bookSearchCriteria.getTitle()));
		}
		
		if (bookSearchCriteria.getAuthor() != null) {
//			restrictions.and(qBookEntity.authors.any().firstName.startsWithIgnoreCase(bookSearchCriteria.getAuthor())
//					.or(qBookEntity.authors.any().lastName.startsWithIgnoreCase(bookSearchCriteria.getAuthor())));
		}
		if (bookSearchCriteria.getLibraryName() != null) {
//			restrictions.and(qBookEntity.library.name.startsWithIgnoreCase(bookSearchCriteria.getLibraryName()));
		}
		return jpaQuery.where(restrictions).list(qBookEntity);
	}
}
