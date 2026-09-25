package models;

public class Booking{

    private String customerName;
    private Movie movie;
    private int seatNumber;
    private Showings showing;

    public Booking(String customerName, Movie movie, int seatNumber){

        this.customerName = customerName;
        this.movie = movie;
        this.seatNumber = seatNumber;
    }
    public Movie getMovie(){
        return movie;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Showings getShowing(){
        return showing;
    }
/*
public String getBookingDetails(){
    return customerName + " booked seat (" + seatNumber + ") for " + movie.getTitle();

    }
 */


}

