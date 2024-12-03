import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {

    private final List<Book> books = new ArrayList<>();

    public Library() {
        books.add(new Book(1231, "Fiona", "Mike", true));
        books.add(new Book(1232, "Shrek", "John", true));
        books.add(new Book(1233, "Lord Farquaad", "Jack", true));
        books.add(new Book(1234, "Prince Charming", "Ivan", false));
    }

    public void addBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book ID: ");
        final int newId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter book title: ");
        final String newTitle = scanner.nextLine();

        System.out.print("Enter book author: ");
        final String newAuthor = scanner.nextLine();


        books.add(new Book(newId, newTitle, newAuthor, false));
        System.out.println("Book added successfully! ");
    }

    public void displayBooks() {
        System.out.println("Library books: ");
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
