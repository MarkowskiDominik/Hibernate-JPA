package pl.spring.demo.repository.predicate;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.types.Predicate;

import pl.spring.demo.criteria.BookSearchCriteria;
import pl.spring.demo.entity.QAuthorEntity;
import pl.spring.demo.entity.QBookEntity;

public class BookSearchPredicate {
	
	private static final QAuthorEntity AUTHORENTITY = QAuthorEntity.authorEntity;
	private static final QBookEntity BOOKENTITY = QBookEntity.bookEntity;

	public BookSearchPredicate() {
	}

	public static BooleanBuilder getBookSearchPredicate(BookSearchCriteria bookSearchCriteria) {
		BooleanBuilder predicate = new BooleanBuilder();
		
		if (bookSearchCriteria.getTitle() != null) {
			predicate.and(BOOKENTITY.title.containsIgnoreCase(bookSearchCriteria.getTitle()));
		}

		if (bookSearchCriteria.getAuthor() != null) {
			Predicate subPredicate = BOOKENTITY.authors.any().in(
				new JPASubQuery()
					.from(AUTHORENTITY)
					.where(AUTHORENTITY.firstName.concat(" ").concat(AUTHORENTITY.lastName)
						.containsIgnoreCase(bookSearchCriteria.getAuthor())
					).list(AUTHORENTITY)
				);

			predicate.and(subPredicate);
		}
		
		if (bookSearchCriteria.getLibraryName() != null) {
			predicate.and(BOOKENTITY.library.name.containsIgnoreCase(bookSearchCriteria.getLibraryName()));
		}

		return predicate;
	}
}
