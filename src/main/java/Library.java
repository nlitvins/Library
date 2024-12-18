import java.util.HashMap;
import java.util.List;

public class Library {

    private final HashMap<Integer, Book> books = new HashMap<>();

    public Library() {
        books.put(1231, new Book(1231, "Fiona", "Mike", true));
        books.put(1232, new Book(1232, "Shrek", "John", true));
        books.put(1233, new Book(1233, "Lord Farquaad", "Jack", true));
        books.put(1234, new Book(1234, "Prince Charming", "Ivan", false));
    }

    public Book addBook(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    public Book findById(int searchId) {
        Book book = books.get(searchId);
        if (book != null) {
            System.out.println("Book found: " + book);
        } else {
            System.out.println("Book with number: " + searchId + " not found.");
        }
        return book;
    }

    public List<Book> findAll() {
        return books.values().stream().toList();
    }

    public void deleteById(int searchId) {
        Book book = books.get(searchId);
        books.remove(searchId, book);
    }
}
