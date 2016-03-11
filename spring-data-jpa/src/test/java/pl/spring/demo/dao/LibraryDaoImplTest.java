package pl.spring.demo.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.spring.demo.entity.LibraryEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonDaoTest-context.xml")
public class LibraryDaoImplTest {

    @Autowired
    private LibraryDao libraryDao;

    @Test
    public void testShouldFindLibraryById() {
        // given
        final long libraryId = 0;
        // when
        LibraryEntity libraryEntity = libraryDao.findOne(libraryId);
        // then
        assertNotNull(libraryEntity);
        assertEquals("Biblioteka Miejska", libraryEntity.getName());
    }

    @Test
    public void testShouldFindLibrarysByName() {
        // given
        final String libraryTitle = "bibl";
        // when
        List<LibraryEntity> librarysEntity = libraryDao.findLibraryByName(libraryTitle);
        // then
        assertNotNull(librarysEntity);
        assertFalse(librarysEntity.isEmpty());
        assertEquals("Biblioteka Miejska", librarysEntity.get(0).getName());
    }
}
