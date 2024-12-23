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
        if (books.putIfAbsent(book.getId(), book) != null) {
            throw new RuntimeException("ID already exists");
        }
        return book;
    }

    public Book findById(int searchId) {
        return books.get(searchId);
    }

    public List<Book> findAll() {
        return books.values().stream().toList();
    }

    public void deleteById(int searchId) {
        Book book = books.get(searchId);
        books.remove(searchId, book);
    }

    public Book updateBookStatus(int searchID) {
        Book book = books.get(searchID);
        if (book == null) {
            throw new RuntimeException("Book don't exist");
        }

        book.setBorrowed(!book.isBorrowed());
        books.put(searchID, book);
        return book;
    }
}
