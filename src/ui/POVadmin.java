package ui;
import models.Movie;
import data.MovieRecords;
import models.Screen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class POVadmin {

    private MovieRecords records;
    private Scanner sc = new Scanner(System.in);


    public POVadmin(MovieRecords records) {
        this.records = records;

    }

    private boolean isBack(String input) {

        return input.equalsIgnoreCase("0");

    }

    private LocalDateTime readDateTime(String prompt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true){
            System.out.println(prompt);
            String input = sc.nextLine();
            try {
                return LocalDateTime.parse(input, formatter);
            }catch (DateTimeParseException e){
                System.out.println("Invalid format. Use yyyy-MM-dd HH:mm.");
        }
            }
    }

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
        public void start() {

            while (true) {
                System.out.println("=== Admin Menu ===");
                System.out.println("1. Add Movie");
                System.out.println("2. Remove Movie");
                System.out.println("3. Add Showing");
                System.out.println("4. Remove Showing");
                System.out.println("5. Add Screen");
                System.out.println("6. Remove Screen");
                System.out.println("7. Back");

                int choice = readInt("Enter your choice: ");

                switch (choice) {
                    case 1 -> addMovies();
                    case 2 -> removeMovies();
                    case 3 -> addShowing();
                    case 4 -> removeShowing();
                    case 5 -> addScreen();
                    case 6 -> removeScreen();
                    case 7 -> {return;}
                    default -> System.out.println("Invalid option.");
                }
            }
        }

    private void addMovies() {
        System.out.println("\n ==== Add Movie Menu ===");

        System.out.println("Enter the Movie ID: " + "\nPress 0 to exit.");
        String id = sc.nextLine();
        if (isBack(id)) {
            return;
        }

        System.out.println("Enter the movie title: "  + "\nPress 0 to exit.");
        String title = sc.nextLine();
        if (isBack(title)) {
            return;
        }
        int durationMinutes = readInt("Enter duration: "  + "\nPress 0 to exit.");
       if (durationMinutes == 0) {
           return;
       }
        System.out.println("Enter description: "  + "\nPress 0 to exit.");
        String description = sc.nextLine();
        if (isBack(description)){
            return;
        }

        int rating = -1;

        while (true) {
            System.out.println("Enter rating (1–10):\nPress 0 to exit.");
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

        sc.nextLine();

        Movie addMovie = new Movie(id, title, durationMinutes, description, rating);
        records.addMovies(addMovie);

    }

    private void removeMovies(){

        System.out.println("\n ==== Remove Movie Menu ===");

        System.out.println("Enter the Movie ID: ");
        String id = sc.nextLine();

        records.removeMoviesById(id);

    }

    public void addShowing(){
        System.out.println("=== Add Showing Menu ===");
        Movie movie = null;

        while (movie == null) {
            System.out.println("Please eneter the Id of the movie that will be showing: " + "\nPress 0 to exit." );
            String movieId = sc.nextLine();
            if (isBack(movieId)) return;

            movie = records.getMovieById(movieId);
            if (movie == null) {
                System.out.println("Error: Movie Id: " + movieId + " does not exist.");
            }
        }
    Screen screen = null;
    while (screen == null) {
        int number = readInt("Enter the Screen number for the showing: " + "\nPress 0 to exit.");
            //issue here


        screen = records.getScreenNumber(number);
        if (screen == null) {
            System.out.println("Error: Screen: " + number + " does not exist.");
        }
    }

        LocalDateTime time = readDateTime("Enter date and time of the showing (yyyy-MM-dd HH:mm): " );
        records.addShowing(movie, time, screen);
    }

    public void removeShowing(){


    }

    public void addScreen(){
        System.out.println("=== Add Screen Menu ===");

        int number = readInt("Enter the Screen Number: ");
        int row = readInt("Enter the number of rows: ");
        int col = readInt("Enter the number of columns: ");

        Screen screen = new Screen(number, row, col);
        records.addScreen(screen);

        System.out.println("Screen number: " + number + " added!");
    }

    public void removeScreen(){
        System.out.println("=== Remove Screen menu ===");
        int number = readInt("Enter the Screen Number to remove:");

        records.removeScreen(number);

    }

}



