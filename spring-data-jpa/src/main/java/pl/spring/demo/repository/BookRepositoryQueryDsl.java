package pl.spring.demo.repository;

import java.util.List;

import pl.spring.demo.criteria.BookSearchCriteria;
import pl.spring.demo.entity.BookEntity;

public interface BookRepositoryQueryDsl {
	
	public List<BookEntity> findBooksBySearchCriteriaQueryDsl(BookSearchCriteria bookSearchCriteria);
}
