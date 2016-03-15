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
import pl.spring.demo.repository.BookRepositoryCustom;

public class BookRepositoryImpl implements BookRepositoryCustom {

	@PersistenceContext(name = "hsql")
	private EntityManager entityManager;

	@Override
	public List<BookEntity> findBooksBySearchCriteria(BookSearchCriteria bookSearchCriteria) {
		QBookEntity qBookEntity = QBookEntity.bookEntity;
		QAuthorEntity qAuthorEntity = QAuthorEntity.authorEntity;

		JPAQuery jpaQuery = new JPAQuery(entityManager).from(qBookEntity);
		BooleanBuilder restrictions = new BooleanBuilder();
		
		if (bookSearchCriteria.getTitle() != null) {
			restrictions.and(qBookEntity.title.containsIgnoreCase(bookSearchCriteria.getTitle()));
		}

		if (bookSearchCriteria.getAuthor() != null) {
			JPAQuery jpaSubQuery = new JPAQuery(entityManager).from(qAuthorEntity)
					.where(qAuthorEntity.firstName.concat(" ")
							.concat(qAuthorEntity.lastName).containsIgnoreCase(bookSearchCriteria.getAuthor()));
			restrictions.and(qBookEntity.authors.any().in(jpaSubQuery.list(qAuthorEntity)));
		}
		
		if (bookSearchCriteria.getLibraryName() != null) {
			restrictions.and(qBookEntity.library.name.containsIgnoreCase(bookSearchCriteria.getLibraryName()));
		}

		return jpaQuery.where(restrictions).list(qBookEntity);
	}
}
