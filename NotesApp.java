package notesapplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * A simple command-line notes manager that uses file I/O
 * to persist data to a text file named "notes.txt".
 */
public class NotesApp {
    private static final String FILE_NAME = "notes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1; // Invalid input, will loop again
            }

            System.out.println("-------------------------------------");

            switch (choice) {
                case 1:
                    addNote(scanner);
                    break;
                case 2:
                    viewNotes();
                    break;
                case 3:
                    System.out.println("Exiting Notes Manager. Goodbye! 👋");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("-------------------------------------");

        } while (choice != 3);

        scanner.close();
    }

    /**
     * Displays the main menu options to the user.
     */
    private static void displayMenu() {
        System.out.println("\n--- Simple Notes Manager ---");
        System.out.println("1. Add a Note (Append)");
        System.out.println("2. View All Notes");
        System.out.println("3. Exit");
    }

    /**
     * Reads a note from the user and appends it to the file.
     * Uses FileWriter (wrapped in PrintWriter for easy line writing).
     * @param scanner The Scanner object for user input.
     */
    private static void addNote(Scanner scanner) {
        System.out.print("Enter your note (max one line): ");
        String note = scanner.nextLine().trim();

        if (note.isEmpty()) {
            System.out.println("Note cannot be empty.");
            return;
        }

        // The 'true' argument enables append mode.
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("- " + note); // Write the note with a prefix
            System.out.println("✅ Note saved successfully.");

        } catch (IOException e) {
            System.err.println("❌ An error occurred while writing the note: " + e.getMessage());
        }
    }

    /**
     * Reads and prints all notes from the file.
     * Uses FileReader (wrapped in BufferedReader for efficient line reading).
     */
    private static void viewNotes() {
        System.out.println("--- Your Notes ---");

        try (FileReader fr = new FileReader(FILE_NAME);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            boolean hasNotes = false;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                hasNotes = true;
            }

            if (!hasNotes) {
                System.out.println("(No notes found. Add one!)");
            }

        } catch (IOException e) {
            // This usually means the file doesn't exist yet, which is fine on first run.
            System.out.println("(No notes file found. Add your first note!)");
        }
    }
}
