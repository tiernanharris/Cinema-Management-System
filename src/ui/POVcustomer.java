package ui;

import models.Movie;
import services.BookingServices;
import data.MovieRecords;
import java.util.Scanner;

public class POVcustomer {

    private Movie movie;
    private MovieRecords records;
    private BookingServices bookingServices;
    private Scanner sc = new Scanner(System.in);

    public POVcustomer(MovieRecords records, BookingServices bookingServices) {
        this.records = records;
        this.bookingServices = bookingServices;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

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
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewMovies() {
        System.out.println("\n ==== Movies ===");
        for (Movie m : records.getMovies()) {
            System.out.println(m.getTitle() + ": " + m.getMovieDetails());
        }


    }

    private void bookSeat() {
        System.out.println("\n === Booking for" + movie.getTitle() + " ===");
        System.out.print("Enter movie ID:");
        String id = sc.nextLine();

        Movie movie = records.getMovieById(id);

        if(movie == null){
            System.out.println("Movie not found.");
            return;
        }
        System.out.println("\n=== Booking for " + movie.getTitle() + " ===");
    }

    private void viewMyBookings() {

    }

}
