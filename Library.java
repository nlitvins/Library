import java.util.Scanner;
import java.util.UUID;

public class Library {
    int[] id = new int[]{1231, 1232, 1233, 1234};
    String[] title = new String[]{"Fiona", "Shrek", "Lord Farquaad", "Prince Charming"};
    String[] author = new String[]{"Mike", "John", "Jack", "Ivan"};
    boolean[] isBorrowed = new boolean[]{true, true, true, false};

    public void addBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter book ID: ");
        int newId = Integer.parseInt(scanner.nextLine());
        scanner.nextLine();

        System.out.print("Enter book title: ");
        String newTitle = scanner.nextLine();
        scanner.nextLine();

        System.out.print("Enter book author: ");
        String newAuthor = scanner.nextLine();
        scanner.nextLine();


        this.id = expandArray(this.id, newId);
        title = expandArray(title, newTitle);
        author = expandArray(author,  newAuthor);
        isBorrowed = expandArray(isBorrowed, false);


        System.out.println("Book added successfully! ");
    }

    public void displayBooks(){
        System.out.println("Library books: ");
        for (int i = 0; i < id.length; i++) {
            System.out.println("ID: " + id[i] + ", Title: " + title[i] + ", Author: " + author[i] + ", Borrowed: " + isBorrowed[i]);
        }
    }

    private int[] expandArray(int[] original, int newElement){
        int[] newArray = new int[original.length + 1];
        System.arraycopy(original, 0, newArray, 0, original.length);
        newArray[original.length] = newElement;
        return newArray;
    }

    private String[] expandArray(String[] original, String newElement){
        String[] newArray = new String[original.length + 1];
        System.arraycopy(original, 0, newArray, 0, original.length);
        newArray[original.length] = newElement;
        return newArray;
    }

    private boolean[] expandArray(boolean[] original, boolean newElement){
     boolean[] newArray = new boolean[original.length +1];
     System.arraycopy(original, 0, newArray, 0, original.length);
     newArray[original.length] = newElement;
     return newArray;
    }
}
