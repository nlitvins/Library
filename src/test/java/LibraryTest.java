import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library sut;

    @BeforeEach
    void setup() {
        sut = new Library();
    }

    @Test
    void addBook() {
        assertNull(sut.findById(1237));
        Book book = new Book(1237, "Idiot", "Chehov", false);

        Book result = sut.addBook(book);

        assertEquals(book, result);
        assertEquals(book, sut.findById(1237));
    }

    @Test
    void whenAddBookExistsThenThrowException() {
        assertNotNull(sut.findById(1231));
        Book book = new Book(1231, "Idiot", "Chehov", false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.addBook(book));
        assertEquals("ID already exists", thrown.getMessage());
    }

    @Test
    void findById() {
        Book result = sut.findById(1233);

        Book expected = new Book(1233, "Lord Farquaad", "Jack", true);
        assertEquals(expected, result);
    }

    @Test
    void findAll() {
        List<Book> result = sut.findAll();

        List<Book> expected = new ArrayList<>();
        expected.add(new Book(1231, "Fiona", "Mike", true));
        expected.add(new Book(1232, "Shrek", "John", true));
        expected.add(new Book(1233, "Lord Farquaad", "Jack", true));
        expected.add(new Book(1234, "Prince Charming", "Ivan", false));
        assertThat(result, containsInAnyOrder(expected.toArray()));
    }

    @Test
    void deleteById() {
        assertNotNull(sut.findById(1231));

        sut.deleteById(1231);

        Book result = sut.findById(1231);
        assertNull(result);
    }

    @Test
    void updateBookStatus() {
        Book result = sut.updateBookStatus(1231);

        Book expected = new Book(1231, "Fiona", "Mike", false);
        assertEquals(expected, result);
    }

    @Test
    void whenUpdateBookStatusExistsThenThrowException() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> sut.updateBookStatus(1255));

        assertEquals("Book don't exist", thrown.getMessage());
    }
}