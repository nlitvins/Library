import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    void findById() {
        Book result = sut.findById(1233);

        Book expected = new Book(1233, "Lord Farquaad", "Jack", true);
        assertEquals(result, expected);
    }

    @Test
    void findAll() {
        List<Book> result = sut.findAll();

        List<Book> expected = new ArrayList<>();
        expected.add(new Book(1231, "Fiona", "Mike", true));
        expected.add(new Book(1232, "Shrek", "John", true));
        expected.add(new Book(1233, "Lord Farquaad", "Jack", true));
        expected.add(new Book(1234, "Prince Charming", "Ivan", false));
        assertEquals(expected, result);
    }
}