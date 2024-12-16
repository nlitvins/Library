import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LibraryTest {

    private final Library sut = new Library();

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
        var result = sut.findById(1233);
    }

    @Test
    void findAll() {
        var result = sut.findAll();
    }


}