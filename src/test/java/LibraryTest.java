import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LibraryTest {

    private final Library sut = new Library();

    @Test
    void addBook() {
        Book idCheck = sut.findById(1237);
        assertNull(idCheck);

        Book book = new Book(1237, "Idiot", "Chehov", false);
        Book result = sut.addBook(book);
        assertEquals(book, result);

        Book idCheck1 = sut.findById(1237);
        assertEquals(book, idCheck1);
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