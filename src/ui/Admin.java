package ui;

import models.Movie;
import data.MovieRecords;
import models.Screen;
import models.Showings;
import services.BookingServices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//-----------------------------------------------------------------------------------------------------

public class Admin {

    private MovieRecords records;
    private Scanner sc = new Scanner(System.in);
    private BookingServices bookingServices;

    public Admin(MovieRecords records, BookingServices bookingServices) {
        this.records = records;
        this.bookingServices = bookingServices;

    }

//-----------------------------------------------------------------------------------------------------

    private boolean isBack(String input) {

        return input.equalsIgnoreCase("0");

    }

//-----------------------------------------------------------------------------------------------------

    private LocalDate readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.println(prompt);
            String input = sc.nextLine().trim();

            if (input.equals("0")) return null;

            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use dd/MM/yyyy.");
            }
        }
    }

    private LocalTime readTime(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (true) {
            System.out.println(prompt);
            String input = sc.nextLine().trim();

            if (input.equals("0")) return null;

            try {
                return LocalTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time. Use HH:mm.");
            }
        }
    }

    private LocalDateTime readDateTime() {
        LocalDate date = readDate("Enter the date of the showing (dd/MM/yyyy): ");
        if (date == null) return null;

        LocalTime time = readTime("Enter the time of the showing (HH:mm): ");
        if (time == null) return null;

        return LocalDateTime.of(date, time);
    }

//-----------------------------------------------------------------------------------------------------

    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

//-----------------------------------------------------------------------------------------------------

    public void start() {

        while (true) {
            System.out.println("=== Admin Menu ===");
            System.out.println("1. Add Movie");
            System.out.println("2. Remove Movie");
            System.out.println("3. Add Showing");
            System.out.println("4. Remove Showing");
            System.out.println("5. Add Screen");
            System.out.println("6. Remove Screen");
            System.out.println("7. List Showings");
            System.out.println("8. List movies");
            System.out.println("9. List Screens");
            System.out.println("10. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addMovies();
                case 2 -> removeMovies();
                case 3 -> addShowing();
                case 4 -> removeShowing();
                case 5 -> addScreen();
                case 6 -> removeScreen();
                case 7 -> listShowings();
                case 8 -> viewAllMovies();
                case 9 -> listScreens();
                case 10 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

//-----------------------------------------------------------------------------------------------------

    private void addMovies() {
        System.out.println("\n ==== Add Movie Menu ===" + "\nPress 0 to exit.");

        System.out.println("Enter the Movie ID: ");
        String id = sc.nextLine();
        if (isBack(id)) {
            return;
        }

        System.out.println("Enter the movie title: ");
        String title = sc.nextLine();
        if (isBack(title)) {
            return;
        }
        int durationMinutes = readInt("Enter duration: ");
        if (durationMinutes == 0) {
            return;
        }
        System.out.println("Enter description: ");
        String description = sc.nextLine();
        if (isBack(description)) {
            return;
        }

        int rating = -1;

        while (true) {
            System.out.println("Enter rating (1–10):");
            String input = sc.nextLine().trim();

            if (isBack(input)) return;

            try {
                rating = Integer.parseInt(input);

                if (rating >= 1 && rating <= 10) {
                    break;  // valid rating
                }

                System.out.println("Error: Rating must be between 1–10.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        Movie addMovie = new Movie(id, title, durationMinutes, description, rating);
        records.addMovies(addMovie);
        records.saveData();

    }

    private void removeMovies() {

        System.out.println("\n ==== Remove Movie Menu ===" + "\nPress 0 to exit.");

        while (true) {
            System.out.println("Enter the Movie ID: ");
            String id = sc.nextLine();
            if (isBack(id)) return;
            try {
                records.removeMoviesById(id);
                records.saveData();
                break;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    private void viewAllMovies() {
        System.out.println("\n=== Movies ===");
        for (Movie m : records.getMovies()) {
            System.out.println(m.getId() + " | " + m.getTitle() + " | " + m.getMovieDetails());
        }
    }

//-----------------------------------------------------------------------------------------------------

    public void addShowing() {
        System.out.println("=== Add Showing Menu ===" + "\nPress 0 to exit.");
        Movie movie = null;

        while (movie == null) {
            System.out.println("Please enter the Id of the movie that will be showing: ");
            String movieId = sc.nextLine();
            if (isBack(movieId)) return;

            movie = records.getMovieById(movieId);
            if (movie == null) {
                System.out.println("Error: Movie Id: " + movieId + " does not exist.");
            }
        }
        Screen screen = null;
        while (screen == null) {
            int number = readInt("Enter the Screen number for the showing: ");
            if (number == 0) return;

            screen = records.getScreenNumber(number);
            if (screen == null) {
                System.out.println("Error: Screen: " + number + " does not exist.");
            }
        }
        while (true) {
            LocalDateTime time = readDateTime();
            if (time == null) return;
            try {
                records.addShowing(movie, time, screen);
                records.saveData();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void removeShowing() {
        System.out.println("=== Remove Showing Menu ===" + "\nPress 0 to exit.");
        Movie movie = null;

        while (movie == null) {
            System.out.println("Please enter the Id of the movie that will no longer be showing: ");
            String movieId = sc.nextLine();
            if (isBack(movieId)) return;

            movie = records.getMovieById(movieId);
            if (movie == null) {
                System.out.println("Error: Movie Id: " + movieId + " does not exist.");
            }
        }
        Screen screen = null;
        while (screen == null) {
            int number = readInt("Enter the Screen number for the showing: ");
            if (number == 0) return;

            screen = records.getScreenNumber(number);
            if (screen == null) {
                System.out.println("Error: Screen: " + number + " does not exist.");
            }
        }
        while (true) {
            LocalDateTime time = readDateTime();
            if (time == null) return;
            try {
                records.removeShowing(movie, time, screen);
                records.saveData();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void listShowings() {
        records.listShowingsByDate();
    }

//-----------------------------------------------------------------------------------------------------

    public void addScreen() {
        System.out.println("=== Add Screen Menu ===" + "\nPress 0 to exit.");

        int number = readInt("Enter the Screen Number: ");
        if (number == 0) return;

        int row;
        while (true) {

            row = readInt("Enter the number of rows: ");
            if (row == 0) return;
            if (row >= 1 && row <= 20) break;
            System.out.println("Error: Rows has to have a range between 1 and 20. Try again.");
        }
        int col;
        while (true) {
            col = readInt("Enter the number of columns: ");
            if (col == 0) return;
            if (col >= 1 && col <= 25) break;
            System.out.println("Error: Columns has to have a range between 1 and 25. Try again.");
        }
        Screen screen = new Screen(number, row, col);
        records.addScreen(screen);
        records.saveData();


    }

    public void removeScreen() {
        System.out.println("=== Remove Screen menu ===" + "\nPress 0 to exit.");
        while (true) {
            int number = readInt("Enter the Screen Number to remove:");

            if (number == 0) return;


            try {
                records.removeScreen(number);
                records.saveData();

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void listScreens() {
        System.out.println("=== Screens ===");

        if (records.getScreens().isEmpty()) {
            System.out.println("No screens available.");
            return;
        }
        for (Screen s : records.getScreens()) {
            System.out.println("Screen: " + s.getNumber() + " | Rows: " + s.getRows() + " | Columns: " + s.getCols());

        }
        System.out.println("Enter a Screen Number to print its layout or 0 to exit: ");
        int choice = readInt("Choice: ");
        if (choice == 0) return;

        Screen screen = records.getScreenNumber(choice);
        if (screen == null) {
            System.out.println("Error: Screen: " + choice + " does not exist.");
            return;
        }
        printScreenLayout(screen);

    }

    private void printScreenLayout(Screen screen) {
        System.out.println("=== Print Screen Layout ===");

        int screenNum = screen.getNumber();

        // Find all showings for that screen
        List<Showings> list = new ArrayList<>();
        for (Showings s : records.getShowings()) {
            if (s.getScreen().getNumber() == screenNum) {
                list.add(s);
            }
        }

        // If no showings exist, print an empty layout
        if (list.isEmpty()) {
            System.out.println("No showings exist for this screen. Printing empty layout:\n");

            int rows = screen.getRows();
            int cols = screen.getCols();

            // Print column numbers
            System.out.print("    ");
            for (int c = 1; c <= cols; c++) {
                System.out.printf("%5d", c);
            }
            System.out.println();

            // Print empty seat grid
            for (int r = 0; r < rows; r++) {
                char rowLetter = (char) ('A' + r);
                System.out.printf("%5s", rowLetter);

                for (int c = 0; c < cols; c++) {
                    System.out.printf("%5s", "[ ]");
                }
                System.out.println();
            }

            return;
        }

        // Otherwise, let admin choose a showing
        System.out.println("Select a showing to print its layout:");
        int index = 1;
        for (Showings s : list) {
            System.out.println(index++ + ". " + s.getMovie().getTitle() + " | " +
                    s.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }

        int choice = readInt("Choice: ");
        if (choice < 1 || choice > list.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Showings selected = list.get(choice - 1);

        bookingServices.printSeatMap(selected);
    }

}



