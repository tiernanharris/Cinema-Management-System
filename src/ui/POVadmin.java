package ui;
import models.Movie;
import data.MovieRecords;
import models.Screen;
import java.util.Scanner;

public class POVadmin {

    private MovieRecords records;
    private Scanner sc = new Scanner(System.in);

    public POVadmin(MovieRecords records) {
        this.records = records;

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

                int choice = sc.nextInt();
                sc.nextLine();

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

        System.out.println("Enter the Movie ID: ");
        String id = sc.nextLine();


        System.out.println("Enter the movie title: ");
        String title = sc.nextLine();

        int durationMinutes;

        while (true) {
            System.out.print("Enter duration: ");
            if (sc.hasNextInt()) {
                durationMinutes = sc.nextInt();
                break;

            } else {
                System.out.println("Error: Please enter the length of the movie in minutes.");
                sc.nextLine(); //*
            }
        }

        System.out.println("Enter description: ");
        String description = sc.nextLine();

        System.out.println("Enter rating: ");
        int rating = sc.nextInt();
        sc.nextLine();

        Movie addMovie = new Movie(id, title, durationMinutes, description, rating);
        records.addMovies(addMovie);

    }

    private void removeMovies(){

        System.out.println("\n ==== Remove Movie Menu ===");

        System.out.println("Enter the Movie ID: ");

        String id = sc.nextLine();

        Movie movie = records.getMovieById(id);
        if (movie == null){
            System.out.println("Movie not found");
            return;
        }

        records.removeMoviesById(id);
        System.out.println("Movie " + '"' + movie.getTitle() + '"' +" has been removed!");
    }

    public void addShowing(){
        System.out.println("=== Add Showing Menu ===");



    }

    public void removeShowing(){


    }

    public void addScreen(){
        System.out.println("=== Add Screen Menu ===");

        System.out.println("Enter the Screen Number: ");
        int number = sc.nextInt();

        System.out.println("Enter the no. of rows.");
        int row = sc.nextInt();

        System.out.println("Enter the no. of columns.");
        int col = sc.nextInt();

        Screen addScreen = new Screen(number, row, col);
        records.addScreen(addScreen);

        System.out.println("Screen number: " + number + " added!");
    }

    public void removeScreen(){
        System.out.println("=== Remove Screen menu ===");
        int number = sc.nextInt();

        Screen screenToRemove = null;
        for (Screen s : records.getScreens()){
            if(s.getNumber() == (number)){
                screenToRemove = s;
                break;
            }
        }
        if(screenToRemove == null){
            System.out.println("Screen not found.");
            return;
        }
        records.removeScreen(screenToRemove);
        System.out.println("Screen number: " + number + " removed!");
    }

}



