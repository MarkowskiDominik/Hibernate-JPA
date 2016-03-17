package pl.spring.demo.service;

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
import org.springframework.transaction.annotation.Transactional;

import pl.spring.demo.criteria.BookSearchCriteria;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
@Transactional(readOnly = true)
public class BookServiceTest {

	@Autowired
	private BookService bookService;

	@Test
	public void testShouldFindAllBooks() {
		// given
		// when
		List<BookTo> booksTo = bookService.findAllBooks();
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals("Pierwsza książka", booksTo.get(0).getTitle());
		assertEquals(12, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByTitle() {
		// given
		final String bookTitle = "pierwsza";
		// when
		List<BookTo> booksTo = bookService.findBooksByTitle(bookTitle);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals("Pierwsza książka", booksTo.get(0).getTitle());
		assertEquals(1, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByAuthorName1() {
		// given
		final String author = "jan";
		// when
		List<BookTo> booksTo = bookService.findBooksByAuthor(author);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals("Pierwsza książka", booksTo.get(0).getTitle());
		assertEquals(7, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByAuthorName2() {
		// given
		final String author = "kowalski";
		// when
		List<BookTo> booksTo = bookService.findBooksByAuthor(author);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals("Pierwsza książka", booksTo.get(0).getTitle());
		assertEquals(4, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByCriteria_AllBooks() {
		// given
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(12, booksTo.size());
	}
	
	@Test
	public void testShouldFindBooksByCriteria() {
		// given
		final String bookTitle = "książka";
		final String author = "kowalski";
		final String libraryName = "główna";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, author, libraryName);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals("Pierwsza książka", booksTo.get(0).getTitle());
		assertEquals(1, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByCriteria_NoResults() {
		// given
		final String bookTitle = "książka abc";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksTo);
		assertTrue(booksTo.isEmpty());
	}

	@Test
	public void testShouldFindBooksByCriteria_Title() {
		// given
		final String bookTitle = "książka";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(4, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByCriteria_Author() {
		// given
		final String author = "jan";
		final BookSearchCriteria criteria = new BookSearchCriteria(null, author, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(7, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByCriteria_Library() {
		// given
		final String libraryName = "filia";
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, libraryName);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaQueryDsl(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(6, booksTo.size());
	}
	
	@Test
	public void testShouldFindBooksByCriteriaPredicateExecutor_AllBooks() {
		// given
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaPredicateExecutor(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(12, booksTo.size());
	}
	
	@Test
	public void testShouldFindBooksByCriteriaPredicateExecutor() {
		// given
		final String bookTitle = "książka";
		final String author = "kowalski";
		final String libraryName = "główna";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, author, libraryName);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaPredicateExecutor(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals("Pierwsza książka", booksTo.get(0).getTitle());
		assertEquals(1, booksTo.size());
	}
	
	@Test
	public void testShouldFindBooksByCriteriaPredicateExecutor_NoResults() {
		// given
		final String bookTitle = "książka abc";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaPredicateExecutor(criteria);
		// then
		assertNotNull(booksTo);
		assertTrue(booksTo.isEmpty());
	}
	
	@Test
	public void testShouldFindBooksByCriteriaPredicateExecutor_Title() {
		// given
		final String bookTitle = "książka";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaPredicateExecutor(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(4, booksTo.size());
	}
	
	@Test
	public void testShouldFindBooksByCriteriaPredicateExecutor_Author() {
		// given
		final String author = "jan";
		final BookSearchCriteria criteria = new BookSearchCriteria(null, author, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaPredicateExecutor(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(7, booksTo.size());
	}
	
	@Test
	public void testShouldFindBooksByCriteriaPredicateExecutor_Library() {
		// given
		final String libraryName = "filia";
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, libraryName);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaPredicateExecutor(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(6, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByCriteriaJinq_AllBooks() {
		// given
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaJinq(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(12, booksTo.size());
	}
	
	@Test
	public void testShouldFindBooksByCriteriaJinq() {
		// given
		final String bookTitle = "książka";
		final String author = "kowalski";
		final String libraryName = "główna";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, author, libraryName);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaJinq(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals("Pierwsza książka", booksTo.get(0).getTitle());
		assertEquals(1, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByCriteriaJinq_NoResults() {
		// given
		final String bookTitle = "książka abc";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaJinq(criteria);
		// then
		assertNotNull(booksTo);
		assertTrue(booksTo.isEmpty());
	}

	@Test
	public void testShouldFindBooksByCriteriaJinq_Title() {
		// given
		final String bookTitle = "książka";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, null, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaJinq(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(4, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByCriteriaJinq_Author() {
		// given
		final String author = "Jan Kowalski";
		final BookSearchCriteria criteria = new BookSearchCriteria(null, author, null);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaJinq(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(7, booksTo.size());
	}

	@Test
	public void testShouldFindBooksByCriteriaJinq_Library() {
		// given
		final String libraryName = "filia";
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, libraryName);
		// when
		List<BookTo> booksTo = bookService.findBooksByCriteriaJinq(criteria);
		// then
		assertNotNull(booksTo);
		assertFalse(booksTo.isEmpty());
		assertEquals(6, booksTo.size());
	}
	
}
