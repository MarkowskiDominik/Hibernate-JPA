package pl.spring.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.spring.demo.criteria.BookSearchCriteria;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.repository.BookRepository;
import pl.spring.demo.repository.predicate.BookSearchPredicate;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<BookTo> findAllBooks() {
		return BookMapper.map2To(bookRepository.findAll());
	}

	@Override
	public List<BookTo> findBooksByTitle(String title) {
		return BookMapper.map2To(bookRepository.findBooksByTitle(title));
	}

	@Override
	public List<BookTo> findBooksByAuthor(String author) {
		return BookMapper.map2To(bookRepository.findBooksByAuthor(author));
	}

	@Override
	public List<BookTo> findBooksByCriteriaQueryDsl(BookSearchCriteria bookSearchCriteria) {
		return BookMapper.map2To(bookRepository.findBooksBySearchCriteriaQueryDsl(bookSearchCriteria));
	}
	
	@Override
	public List<BookTo> findBooksByCriteriaPredicateExecutor(BookSearchCriteria bookSearchCriteria) {
		return BookMapper.map2To(StreamSupport.stream(
				bookRepository.findAll(BookSearchPredicate.getBookSearchPredicate(bookSearchCriteria)).spliterator(),
				false).collect(Collectors.toList()));
	}

	@Override
	public List<BookTo> findBooksByCriteriaJinq(BookSearchCriteria bookSearchCriteria) {
		return BookMapper.map2To(bookRepository.findBooksBySearchCriteriaJinq(bookSearchCriteria));
	}

	@Override
	@Transactional(readOnly = false)
	public BookTo saveBook(BookTo book) {
		BookEntity entity = BookMapper.map(book);
		entity = bookRepository.save(entity);
		return BookMapper.map(entity);
	}
}
