package models;

public class Booking {

    private String customerName;
    private Showings showings;
    private  int row;
    private  int col;

    public Booking(String customerName, Showings showings, int row, int col){
        this.customerName = customerName;
        this.showings = showings;
        this.row = row;
        this.col = col;
    }
    public String getCustomerName() { return customerName; }
    public Showings getShowing() { return showings; }
    public int getRow() { return row; }
    public int getCol() { return col; }
}
