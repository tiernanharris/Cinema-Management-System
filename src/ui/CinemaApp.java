package ui;
import data.MovieRecords;
import models.Movie;
import services.BookingServices;

import java.awt.print.Book;
import java.util.Scanner;


public class CinemaApp {

    private MovieRecords records;
    private BookingServices bookingService;

    public CinemaApp(MovieRecords records, BookingServices bookingService) {
        this.records = records;
        this.bookingService = bookingService;
    }
public static void main(String[] args){

    MovieRecords records = new MovieRecords();
    BookingServices bookingServices = new BookingServices();

    CinemaApp app = new CinemaApp(records, bookingServices);
    app.start();
}
    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== Cinema System ===");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> new POVcustomer(records, bookingService).start();
                case 2 -> new POVadmin(records).start();
                case 3 -> System.exit(0);
            }
        }

    }
}

