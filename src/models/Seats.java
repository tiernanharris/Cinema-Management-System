package models;

public class Seats {

    private int row;
    private int col;
    private boolean booked;

    public Seats(int row, int col){

        this.row = row;
        this.col = col;
        this.booked = false;        //No seats are booked

    }

    public boolean isBooked(){
        return booked;
    }

    public void cancel(){
        booked = false;             //Returns booked seat to available
    }

    public String bookability(){
        return booked ? "[ X ]" : "[ O ]";
    }
}
