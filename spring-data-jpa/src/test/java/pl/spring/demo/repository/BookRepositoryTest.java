package pl.spring.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.criteria.BookSearchCriteria;
import pl.spring.demo.entity.BookEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonRepositoryTest-context.xml")
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void testShouldFindBookById() {
		// given
		final long bookId = 4;
		// when
		BookEntity bookEntity = bookRepository.findOne(bookId);
		// then
		assertNotNull(bookEntity);
		assertEquals("Pierwsza książka", bookEntity.getTitle());
	}

	@Test
	public void testShouldFindBooksByTitle() {
		// given
		final String bookTitle = "pierwsza";
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksByTitle(bookTitle);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
		assertEquals(1, booksEntity.size());
	}

	@Test
	public void testShouldFindBooksByAuthorName1() {
		// given
		final String author = "jan";
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksByAuthor(author);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
		assertEquals(7, booksEntity.size());
	}

	@Test
	public void testShouldFindBooksByAuthorName2() {
		// given
		final String author = "kowalski";
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksByAuthor(author);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
		assertEquals(4, booksEntity.size());
	}

	@Test
	public void testShouldFindBooksByCriteriaAllBooks() {
		// given
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, null);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals(12, booksEntity.size());
	}
	
	@Test
	public void testShouldFindBooksByCriteria() {
		// given
		final String bookTitle = "książka";
		final String author = "kowalski";
		final String libraryName = "główna";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, author, libraryName);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
		assertEquals(1, booksEntity.size());
	}

	@Test
	public void testShouldFindBooksByCriteriaNoResults() {
		// given
		final String bookTitle = "książka abc";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, null, null);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksEntity);
		assertTrue(booksEntity.isEmpty());
	}

	@Test
	public void testShouldFindBooksByCriteriaTitle() {
		// given
		final String bookTitle = "książka";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, null, null);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals(4, booksEntity.size());
	}

	@Test
	public void testShouldFindBooksByCriteriaAuthor() {
		// given
		final String author = "jan";
		final BookSearchCriteria criteria = new BookSearchCriteria(null, author, null);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals(7, booksEntity.size());
	}

	@Test
	public void testShouldFindBooksByCriteriaLibrary() {
		// given
		final String libraryName = "filia";
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, libraryName);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals(6, booksEntity.size());
	}
}
