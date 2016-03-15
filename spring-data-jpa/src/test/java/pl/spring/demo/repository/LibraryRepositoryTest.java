package pl.spring.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.entity.LibraryEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonRepositoryTest-context.xml")
public class LibraryRepositoryTest {

	@Autowired
	private LibraryRepository libraryRepository;
	@Autowired
	private BookRepository bookRepository;

	@Test
	public void testShouldFindLibraryById() {
		// given
		final long libraryId = 0;
		// when
		LibraryEntity libraryEntity = libraryRepository.findOne(libraryId);
		// then
		assertNotNull(libraryEntity);
		assertEquals("Biblioteka Miejska", libraryEntity.getName());
	}

	@Test
	public void testShouldFindLibrarysByName() {
		// given
		final String libraryTitle = "bibl";
		// when
		List<LibraryEntity> librarysEntity = libraryRepository.findLibraryByName(libraryTitle);
		// then
		assertNotNull(librarysEntity);
		assertFalse(librarysEntity.isEmpty());
		assertEquals("Biblioteka Miejska", librarysEntity.get(0).getName());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testShouldDeleteLibraryById() {
		// given
		final long libraryId = 0;
		List<BookEntity> booksBeforeDelete = bookRepository.findAll();
		// when
		libraryRepository.delete(libraryId);
		List<BookEntity> booksAfterDelete = bookRepository.findAll();
		// then
		assertNull(libraryRepository.findOne(libraryId));
		assertNotEquals(booksBeforeDelete.size(), 0);
		assertNotEquals(booksBeforeDelete.size(), booksAfterDelete.size());
		assertTrue(booksAfterDelete.isEmpty());
	}

	@Test(expected = DataAccessException.class)
	@Transactional
	@Rollback(true)
	public void testShouldDeleteTwiceTheSameLibraryById() {
		// given
		final long libraryId = 0;
		// when
		assertNotNull(libraryRepository.findOne(libraryId));
		libraryRepository.delete(libraryId);
		assertNull(libraryRepository.findOne(libraryId));
		libraryRepository.delete(libraryId);
		// then
	}
}
