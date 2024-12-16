import org.junit.jupiter.api.Test;

class LibraryTest {

    private final Library sut = new Library();

    //Проверка по ид, добавление книги, Проверка по ид.
    @Test
    void addBook() {
        Book book = new Book(1237, "Idiot", "Chehov", false);

        var result = sut.addBook(book);

//        assertEquals();
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