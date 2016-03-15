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
		final long bookId = 1;
		// when
		BookEntity bookEntity = bookRepository.findOne(bookId);
		// then
		assertNotNull(bookEntity);
		assertEquals("Pierwsza książka", bookEntity.getTitle());
	}

	@Test
	public void testShouldFindBooksByTitle() {
		// given
		final String bookTitle = "p";
		// when
		List<BookEntity> booksEntity = bookRepository.findBookByTitle(bookTitle);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
	}

	@Test
	public void testShouldFindBooksByAuthorFirstName() {
		// given
		final String author = "jan";
		// when
		List<BookEntity> booksEntity = bookRepository.findBookByAuthor(author);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
	}

	@Test
	public void testShouldFindBooksByAuthorLastName() {
		// given
		final String author = "kowalski";
		// when
		List<BookEntity> booksEntity = bookRepository.findBookByAuthor(author);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
	}

	@Test
	public void testShouldFindBooksByCriteria() {
		// given
		final String bookTitle = "pierwsza";
		final String author = "kowalski";
		final String libraryName = "biblioteka";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, author, libraryName);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteria(criteria);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
	}

	@Test
	public void testShouldFindBooksByCriteriaNoResults() {
		// given
		final String bookTitle = "pierwsza";
		final String author = "kowalski";
		final String libraryName = "miejska";
		final BookSearchCriteria criteria = new BookSearchCriteria(bookTitle, author, libraryName);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteria(criteria);
		// then
		assertNotNull(booksEntity);
		assertTrue(booksEntity.isEmpty());
	}

	@Test
	public void testShouldFindBooksByCriteriaAllBooks() {
		// given
		final BookSearchCriteria criteria = new BookSearchCriteria(null, null, null);
		// when
		List<BookEntity> booksEntity = bookRepository.findBooksBySearchCriteria(criteria);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals(3, booksEntity.size());
	}
}
