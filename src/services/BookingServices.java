package services;

import data.MovieRecords;
import models.Seats;
import models.Showings;

public class BookingServices {
    private MovieRecords records;

    public BookingServices(){
        this.records = records;
    }

    public void bookSeat(Showings showtime, int row, int col, String customerName){
//if statement booked seat
//books seat




    }
    public void cancelSeat(Showings showtime, int row, int col, String customerName){


//Check if seat is booked
//Cancl it
    }

    public void isSeatAvailable(Showings showtime, int row, int col){
// if statement is seat free
    }

    public void validateSeat(int row, int col, Seats[][] seats){
      //checks if row and col exists
    }

}
