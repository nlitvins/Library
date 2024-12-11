import org.junit.jupiter.api.Test;

class LibraryTest {

    private final Library sut = new Library();

    @Test
    void addBook() {
        var result = sut.addBook(1237, "Idiot", "Chehov");
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