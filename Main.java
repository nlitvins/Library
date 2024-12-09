import java.util.Scanner;

public class Main{

    public static void addBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book ID: ");
        final int newId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter book title: ");
        final String newTitle = scanner.nextLine();

        System.out.print("Enter book author: ");
        final String newAuthor = scanner.nextLine();

        Library.books.add(new Book(newId, newTitle, newAuthor, false));
        System.out.println("Book added successfully! ");
    }

    public static void main(String[] args){
        Library library = new Library();
        library.displayBooks();
        addBook();
        library.displayBooks();
        library.findAll();
    }
}