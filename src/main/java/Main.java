import java.util.Scanner;

public class Main {

    private static final Library library = new Library();
    private static final Scanner scanner = new Scanner(System.in);

    private static void addBook() {

        System.out.print("Enter book ID: ");
        final int newId = readInt();

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

    private static int readInt() {
        do {
            String value = scanner.nextLine();
            try {
                return Integer.parseInt(value);
            } catch (Exception ignored) {
                System.out.print("Input must be an integer: ");
            }
        } while (true);
    }

    private static void actionMenu() {
        System.out.println("Add new book - print 1");
        System.out.println("Display books - print 2");
        System.out.println("Find book by id - print 3");
        System.out.println("Delete book by id - print 4");
        System.out.println("Update book status - print 5");
        System.out.println("Exit - print 6 ");



        System.out.print("Choose action: ");
        String action = (scanner.nextLine());

        switch (action) {
            case "1" -> {
                addBook();
                actionMenu();
            }
            case "2" -> {
                displayBooks();
                actionMenu();
            }
            case "3" -> {
                searchBookById();
                actionMenu();
            }
            case "4" -> {
                deleteBookById();
                actionMenu();
            }
            case "5" -> {
                updateBookStatusById();
                actionMenu();
            }
            case "6" -> {
                return;
            }
            case null, default -> {
                System.out.println("Incorrect input");
                actionMenu();
            }
        }
    }

    private static void searchBookById() {

        System.out.println("Enter book ID: ");
        int searchId = readInt();
        Book book = library.findById(searchId);
        if (book == null) {
            System.out.println("Book don't exist");
        } else {
            System.out.println(book);
        }
    }

    private static void deleteBookById() {

        System.out.println("Enter book ID: ");
        int deleteId = readInt();
        library.deleteById(deleteId);
        System.out.println("Book with id " + deleteId + " deleted ");
    }

    private static void updateBookStatusById() {

        System.out.println("Enter book ID: ");
        int updateId = readInt();
        library.updateBookStatus(updateId);
    }

    private static void displayBooks() {
        System.out.println("Library books: ");
        for (Book book : library.findAll()) {
            System.out.println(book);
        }
    }

    public static void main(String[] args) {
        actionMenu();
    }
}