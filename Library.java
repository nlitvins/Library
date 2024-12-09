import java.util.ArrayList;
import java.util.List;

public class Library {

    public static final List<Book> books = new ArrayList<>();

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

    public List<Book> findAll() {
        return books;
    }
}
