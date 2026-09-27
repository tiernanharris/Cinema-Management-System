package ui;

import models.Booking;
import models.Movie;
import models.Showings;
import services.BookingServices;
import data.MovieRecords;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

//---------------------------------------------------------------------------------------------------------

public class Customer {

    private Movie movie;
    private MovieRecords records;
    private BookingServices bookingServices;
    private Scanner sc = new Scanner(System.in);

    private int convertRowLetter(String letter, int totalRows) {
        if (letter.length() != 1) return -1;

        char ch = Character.toUpperCase(letter.charAt(0));

        int row = ch - 'A';   // A → 0, B → 1, C → 2 ...

        if (row < 0 || row >= totalRows) return -1;

        return row;
    }

//---------------------------------------------------------------------------------------------------------

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
        if (date == null)return null;

        LocalTime time = readTime("Enter the time of the showing (HH:mm): ");
        if (time == null)return null;

        return LocalDateTime.of(date, time);
    }

//---------------------------------------------------------------------------------------------------------

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

//---------------------------------------------------------------------------------------------------------

    private Showings chooseShowing() {

        while (true) {
            System.out.println("Enter the title of the movie (or 0 to exit): ");
            String title = sc.nextLine().trim();
            if (title.equals("0"))return null;

            int screenNum = readInt("Enter the Screen Number (or 0 to exit):  ");
            if (screenNum == 0)return null;

            LocalDateTime time = readDateTime();
            if (time == null)return null;

            time = time.withSecond(0).withNano(0);

            Showings showing = records.findShowing(title, screenNum, time);

            if (showing == null) {
                System.out.println("Error: Showing not found.");
                continue;
            }

            return showing;

        }
    }

    public Customer(MovieRecords records, BookingServices bookingServices) {
        this.records = records;
        this.bookingServices = bookingServices;
    }

//---------------------------------------------------------------------------------------------------------

    public void start() {

        while (true) {
            System.out.println("=== Customer Menu ===");
            System.out.println("1. View Movies");
            System.out.println("2. Book a Seat");
            System.out.println("3. View My Bookings");
            System.out.println("4. Back");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewMovies();
                case 2 -> bookSeat();
                case 3 -> viewMyBookings();
                case 4 -> {return;}
                default -> System.out.println("Invalid option.");
            }
        }
    }

//---------------------------------------------------------------------------------------------------------

    private void viewMovies() {
        while (true) {
            System.out.println("==== Movies ===");
            System.out.println("1. Search Showings by Title");
            System.out.println("2. View all Showings");
            System.out.println("3. View all movies");
            System.out.println("4. Back ");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> searchShowingsByTitle();
                case 2 -> records.listShowingsByDate();
                case 3 -> viewAllMovies();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewAllMovies(){
        System.out.println("\n=== Movies ===");
        for (Movie m : records.getMovies()){
            System.out.println(m.getTitle() + " | " + m.getMovieDetails());
        }
    }

//---------------------------------------------------------------------------------------------------------

    private void searchShowingsByTitle() {
        while (true) {
            System.out.println("Enter movie title (or 0 to go back): ");
            String title = sc.nextLine().trim();
            if (title.equalsIgnoreCase("0")) return;

            Movie movie = records.getMovieByTitle(title);
            if (movie == null) {
                System.out.println("Error: " + title + " does not exist.");
                continue;
            }
            records.listShowingsByMovieTitle(title);
            records.saveData();

        }
    }

//---------------------------------------------------------------------------------------------------------

    private void bookSeat() {
        Showings showings = chooseShowing();
        if (showings == null) return;

        System.out.println("\n === Booking ===");
        bookingServices.printSeatMap(showings);

        System.out.println("Enter your name: " + "\nPress 0 to exit.");
        String name = sc.nextLine();
        if (name.equals("0"))return;
  String rowLetter ="";
  int row = -1;

  while (row == -1){
      System.out.println("Enter row letter: ");
      rowLetter = sc.nextLine().trim().toUpperCase();

      row = convertRowLetter(rowLetter, showings.getScreen().getRows());

      if (row == -1){
          System.out.println("Invalid row letter. Try again.");
      }
  }
        int col = readInt("Enter column: ");

        if (col == 0)return;

        if (bookingServices.bookSeat(showings,row,col, name)){
            System.out.println("Seat booked successfully!");
            records.saveData();
        }else {
            System.out.println("Seat unavailable or invalid.");
        }
    }

//---------------------------------------------------------------------------------------------------------

    private void viewMyBookings() {
        System.out.println("=== My Bookings ===");
        System.out.println("Enter your name: ");
        String name = sc.nextLine();

        var list = records.getBookingsForCustomer(name);

        if (list.isEmpty()){
            System.out.println("You have no bookings.");
            return;
        }

        int index = 1;
        for (Booking b : list) {

            char rowLetter = (char) ('A' + b.getRow());
            int colNumber = b.getCol() + 1;

            System.out.println(index++ + ". " + b.getShowing().getMovie().getTitle() + " | " +
                    "Screen " + b.getShowing().getScreen().getNumber() + " | " +
                    b.getShowing().getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                    " | Seat (Row: " + rowLetter + " | Column: " + colNumber + ")");
        }

        System.out.println("\nEnter the number of a booking to cancel, or 0 to go back:");
                int choice = readInt("Choice: ");
                if (choice == 0)return;
        if (choice < 1 || choice > list.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Booking selected = list.get(choice - 1);
        char rowLetter = (char)('A' + selected.getRow());
        int colNumber = selected.getCol() + 1;

        bookingServices.cancelSeat(
                selected.getShowing(),
                selected.getRow(),
                selected.getCol(),
                name
        );
        System.out.println(
                "Booking for " + selected.getShowing().getMovie().getTitle() +
                        " at " + selected.getShowing().getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                        " | Row: " + rowLetter +
                        " | Column: " + colNumber +
                        " cancelled."
        );
        records.saveData();

    }

//---------------------------------------------------------------------------------------------------------

}
