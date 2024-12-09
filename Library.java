import java.util.ArrayList;
import java.util.List;

public class Library {

    private static final List<Book> books = new ArrayList<>();

    public Library() {
        books.add(new Book(1231, "Fiona", "Mike", true));
        books.add(new Book(1232, "Shrek", "John", true));
        books.add(new Book(1233, "Lord Farquaad", "Jack", true));
        books.add(new Book(1234, "Prince Charming", "Ivan", false));
    }

    public void displayBooks() {
        System.out.println("Library books: ");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    //    library.books.add(new Book(newId, newTitle, newAuthor, false));
    public Book addBook(int newId, String newTitle, String newAuthor) {
//        for (Book book : books) {
        Book book = new Book(newId, newTitle, newAuthor, false);
        books.add(book);
        return book;
//        }

    }

    public Book findById(int searchId) {
        for (Book book : books) {
            if (searchId == book.getId()) {
                return book;
            }
        }
        return null;
    }

    public List<Book> findAll() {
        return books;
    }
}
