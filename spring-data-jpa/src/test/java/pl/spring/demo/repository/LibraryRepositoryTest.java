package pl.spring.demo.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.entity.LibraryEntity;

import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonRepositoryTest-context.xml")
public class LibraryRepositoryTest {

	@Autowired
	private LibraryRepository libraryRepository;
	@Autowired
	private BookRepository bookRepository;

	@Test
	public void testShouldDeleteLibraryById() {
		// given
		final long libraryId = 0;
		int numberOfBooks = bookRepository.findAll().size();
		// when
		libraryRepository.delete(libraryId);
		List<BookEntity> books = bookRepository.findAll();
		// then
		assertNull(libraryRepository.findOne(libraryId));
		assertNotEquals(numberOfBooks, books.size());
		assertTrue(books.isEmpty());
	}
	
	@Test(expected = DataAccessException.class)
	public void testShouldDeleteTwiceTheSameLibraryById() {
		// given
		final long libraryId = 0;
		// when
		libraryRepository.delete(libraryId);
		assertNull(libraryRepository.findOne(libraryId));
		libraryRepository.delete(libraryId);
		// then
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
}
