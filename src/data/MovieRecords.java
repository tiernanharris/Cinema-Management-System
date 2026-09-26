package data;

import models.Booking;
import models.Movie;
import models.Screen;
import models.Showings;
import java.util.ArrayList;
import java.util.List;


public class  MovieRecords {

    private List<Movie> movies = new ArrayList<>();
    private List<Screen> screens = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public void addMovies(Movie movie){
        if (checkIds(movie.getId())){
            System.out.println("Error: Movie Id already exists.");
        return;
       }
        movies.add(movie);
        System.out.println("Movie " + '"' + movie.getTitle() + '"' + " has been added!");
    }

    public Movie getMovieById(String id){
        for(Movie m : movies){
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    public void removeMoviesById(String id){
        Movie m = getMovieById(id);
        if(m != null){
            movies.remove(m);
        }
    }

  /*  public void removeMovies(Movie movie){
        movies.remove(movie);
    }*/
    public boolean checkIds(String id){
       for (Movie m : movies){
           if (m.getId().equalsIgnoreCase(id)){
               return true;
           }
       }
       return false;
    }
    public List<Movie> getMovies(){
        return movies;
    }

  /*  public Movie getMovieByTitle(String title){
        for(Movie m : movies){
            if(m.getTitle().equalsIgnoreCase(title))
                return m;
        }
        return null;
    }*/

    public List<Screen> getScreens(){
        return screens;
    }

    public void addScreen(Screen screen){
        screens.add(screen);
    }

    public void removeScreen(Screen screen){
        screens.remove(screen);
    }

    public List<Booking> getBookingForMovie(Movie movie){
        List<Booking> result = new ArrayList<>();

        for (Booking b : bookings) {
            if (b.getMovie().equals(movie)) {
                result.add(b);
            }
        }

        return result;
    }

    public List<Booking> getBookingsForShowings(Showings showing){
     List<Booking> result = new ArrayList<>();

        for (Booking b : bookings) {
            if (b.getShowing().equals(showing)) {
                result.add(b);
            }
        }

        return result;
    }
}




