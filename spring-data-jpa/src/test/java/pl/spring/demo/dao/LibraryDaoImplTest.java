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
import pl.spring.demo.entity.LibraryEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonDaoTest-context.xml")
public class LibraryDaoImplTest {

	@Autowired
	private LibraryDao libraryDao;
	@Autowired
	private BookDao bookDao;
	
	@Test
	public void testShouldFindAllLibrary() {
		// given
		// when
		List<LibraryEntity> librarysEntity = libraryDao.findAll();
		// then
		assertNotNull(librarysEntity);
		assertEquals(4, librarysEntity.size());
	}

	@Test
	public void testShouldFindLibraryById() {
		// given
		final long libraryId = 1;
		// when
		LibraryEntity libraryEntity = libraryDao.findOne(libraryId);
		// then
		assertNotNull(libraryEntity);
		assertEquals("Biblioteka Miejska", libraryEntity.getName());
	}

	@Test
	public void testShouldFindLibrarysByName() {
		// given
		final String libraryTitle = "Biblioteka M";
		// when
		List<LibraryEntity> librarysEntity = libraryDao.findLibraryByName(libraryTitle);
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
		List<BookEntity> booksBeforeDelete = bookDao.findAll();
		// when
		libraryDao.delete(libraryId);
		List<BookEntity> booksAfterDelete = bookDao.findAll();
		List<LibraryEntity> librarysAfterDelete = libraryDao.findAll();
		// then
		assertNull(libraryDao.findOne(libraryId));
		assertEquals(12, booksBeforeDelete.size());
		assertEquals(9, booksAfterDelete.size());
		assertEquals(3, librarysAfterDelete.size());
	}

	@Test(expected = EntityNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testShouldDeleteTwiceTheSameLibraryById() {
		// given
		final long libraryId = 0;
		// when
		assertNotNull(libraryDao.findOne(libraryId));
		libraryDao.delete(libraryId);
		assertNull(libraryDao.findOne(libraryId));
		libraryDao.delete(libraryId);
		// then
	}
}
