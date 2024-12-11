import java.util.Scanner;

public class Main{

    private static final Library library = new Library();

    public static void addBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book ID: ");
        final int newId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter book title: ");
        final String newTitle = scanner.nextLine();

        System.out.print("Enter book author: ");
        final String newAuthor = scanner.nextLine();

        Book book = library.addBook(new Book(newId, newTitle, newAuthor, false));
        System.out.println("Book added successfully: ");
        System.out.println(book);
    }

    public static void searchId() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book ID: ");
        int searchId = Integer.parseInt(scanner.nextLine());
    }

    public static void displayBooks() {
        System.out.println("Library books: ");
        for (Book book : library.findAll()) {
            System.out.println(book);
        }
    }

    public static void main(String[] args){
        displayBooks();
        addBook();
        displayBooks();
        Book result = library.findById(1221);
        library.findAll();
    }
}