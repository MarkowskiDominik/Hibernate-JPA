package pl.spring.demo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.spring.demo.entity.BookEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonDaoTest-context.xml")
public class BookDaoImplTest {

	@Autowired
	private BookDao bookDao;
	
	@Test
	public void testShouldFindAllBook() {
		// given
		// when
		List<BookEntity> booksEntity = bookDao.findAll();
		// then
		assertNotNull(booksEntity);
		assertEquals(12, booksEntity.size());
	}

	@Test
	public void testShouldFindBookById() {
		// given
		final long bookId = 4;
		// when
		BookEntity bookEntity = bookDao.findOne(bookId);
		// then
		assertNotNull(bookEntity);
		assertEquals("Pierwsza książka", bookEntity.getTitle());
	}

	@Test
	public void testShouldFindBooksByTitle() {
		// given
		final String bookTitle = "Pierwsza k";
		// when
		List<BookEntity> booksEntity = bookDao.findBookByTitle(bookTitle);
		// then
		assertNotNull(booksEntity);
		assertFalse(booksEntity.isEmpty());
		assertEquals("Pierwsza książka", booksEntity.get(0).getTitle());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testShouldDeleteBookById() {
		// given
		final long bookId = 5;
		List<BookEntity> booksBeforeDelete = bookDao.findAll();
		// when
		bookDao.delete(bookId);
		List<BookEntity> booksAfterDelete = bookDao.findAll();
		// then
		assertNull(bookDao.findOne(bookId));
		assertEquals(12, booksBeforeDelete.size());
		assertEquals(11, booksAfterDelete.size());
	}

	@Test(expected = EntityNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testShouldDeleteTwiceTheSameBookById() {
		// given
		final long bookId = 5;
		// when
		assertNotNull(bookDao.findOne(bookId));
		bookDao.delete(bookId);
		assertNull(bookDao.findOne(bookId));
		bookDao.delete(bookId);
		// then
	}
}
