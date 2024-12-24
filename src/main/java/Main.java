import java.util.Scanner;

public class Main {

    private static final Library library = new Library();

    public static void addBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book ID: ");
        final int newId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter book title: ");
        final String newTitle = scanner.nextLine();

        System.out.print("Enter book author: ");
        final String newAuthor = scanner.nextLine();

        try {
            Book book = library.addBook(new Book(newId, newTitle, newAuthor, false));
            System.out.println("Book added successfully: ");
            System.out.println(book);
        } catch (RuntimeException exception) {
            System.err.println("Book can't be added: " + exception.getMessage());
        }
    }

    public static void actionMenu() {
        System.out.println("Add new book - print 1");
        System.out.println("Display books - print 2");
        System.out.println("Find book by id - print 3");
        System.out.println("Delete book by id - print 4");
        System.out.println("Update book status - print 5");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose action: ");
        final int action = Integer.parseInt(scanner.nextLine());

        if (action == 1) {
            addBook();
            actionMenu();
        } else if (action == 2) {
            displayBooks();
            actionMenu();
        } else if (action == 3) {
            searchBookById();
            actionMenu();
        } else if (action == 4) {
            library.deleteById(1234);
            actionMenu();
        } else {
            System.out.println("Incorrect input");
            actionMenu();
        }
    }


    public static void searchBookById() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book ID: ");
        int searchId = Integer.parseInt(scanner.nextLine());
        Book book = library.findById(searchId);
        if (book == null) {
            System.out.println("Book don't exist");
        } else {
            System.out.println(book);
        }
    }

    public static void deleteBookById() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book ID: ");
        int searchId = Integer.parseInt(scanner.nextLine());
        library.deleteById(searchId);
    }


    public static void displayBooks() {
        System.out.println("Library books: ");
        for (Book book : library.findAll()) {
            System.out.println(book);
        }
    }


    public static void main(String[] args) {
        actionMenu();
//        displayBooks();
//        addBook();
//        displayBooks();
        Book result = library.findById(1231);
//        library.findAll();
    }
}