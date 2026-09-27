package services;

import models.Screen;
import models.Seats;
import models.Showings;
import models.Booking;
import data.MovieRecords;

//---------------------------------------------------------------------------------------------------------

public class BookingServices {

    private MovieRecords records;

    private boolean validateSeats(Screen screen, int row, int col) {
        return row >= 0 && row < screen.getRows() &&
                col >= 0 && col < screen.getCols();
    }

    public BookingServices(MovieRecords records){
        this.records = records;
    }
    public boolean isSeatAvailable(Showings showing, int row, int col) {
        Seats[][] seats = showing.getSeats();

        if (!validateSeats(showing.getScreen(), row, col)) {
            return false;
        }

        return !seats[row][col].isBooked();
    }

    public boolean bookSeat(Showings showing, int row, int col, String customerName) {
        Seats[][] seats = showing.getSeats();

        if (!validateSeats(showing.getScreen(), row, col)) {
            return false;
        }

        if (seats[row][col].isBooked()) {
            return false; // seat already booked
        }

        seats[row][col].book();
        records.addBooking(new Booking(customerName, showing, row, col));
        return true;
    }

    public boolean cancelSeat(Showings showing, int row, int col, String customerName) {
        Seats[][] seats = showing.getSeats();

        if (!validateSeats(showing.getScreen(), row, col)) {
            return false;
        }

        if (!seats[row][col].isBooked()) {
            return false; // seat not booked
        }

        seats[row][col].cancel();

        Booking toRemove = records.findBooking(customerName, showing, row, col);
        if (toRemove != null){
            records.removeBooking(toRemove);
        }
        return true;
    }

//---------------------------------------------------------------------------------------------------------

    public void printSeatMap(Showings showing) {
        int rows = showing.getScreen().getRows();
        int cols = showing.getScreen().getCols();

        // Column numbers
        System.out.print("    ");
        for (int c = 1; c <= cols; c++) {
            System.out.printf("%5d", c);
        }
        System.out.println();

        // Rows + seats
        for (int r = 0; r < rows; r++) {
            char rowLetter = (char) ('A' + r);
            System.out.printf("%5s", rowLetter); // FIXED SPACING

            for (int c = 0; c < cols; c++) {
                boolean booked = showing.getSeats()[r][c].isBooked();
                String seat = booked ? "[ X ]" : "[ O ]";
                System.out.printf("%5s", seat);
            }
            System.out.println();
        }
    }


//---------------------------------------------------------------------------------------------------------
}

