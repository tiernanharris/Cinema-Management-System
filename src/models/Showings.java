package models;

import java.time.LocalDateTime;

public class Showings {

    private Movie movie;
    private LocalDateTime time;
    private Screen screen;
    private  Seats [][] seats;

    public Showings(Movie movie, LocalDateTime time, Screen screen) {
        this.movie = movie;
        this.time = time;

        int rows = screen.getRows();
        int cols = screen.getCols();

        seats = new Seats[rows][cols];

        for(int r = 0; r < rows; r++){                  //recreates screen layout
            for(int c = 0; c < cols; c++){
                seats[r][c] = new Seats(r, c);
            }
        }

    }


}
