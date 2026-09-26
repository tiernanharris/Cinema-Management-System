package data;

import models.Booking;
import models.Movie;
import models.Screen;
import models.Showings;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class  MovieRecords {

    private List<Showings> showings = new ArrayList<>();
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
            System.out.println( m.getTitle() + " has been removed.");

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

        if (checkScreenNumber(screen.getNumber())){
            System.out.println("Error: Screen " + screen.getNumber() + " Id already exists.");
            return;
        }
        screens.add(screen);
        System.out.println("Movie " + '"' + screen.getNumber() + '"' +" has been removed!");
    }
    public Screen getScreenNumber(int number){
        for(Screen s : screens){
            if (s.getNumber() == number){
                return s;
            }
        }
        return null;
    }

    public boolean checkScreenNumber(int number){
        for (Screen s : screens){
            if (s.getNumber() == number){
                return true;
            }
        }
        return false;
    }

    public void removeScreen(int number){
        Screen screenToRemove = getScreenNumber(number);

        if(screenToRemove == null){
            System.out.println("Screen " + number + " was not found.");
            return;
        }
        screens.remove(screenToRemove);
        System.out.println("Screen number: " + number + " removed!");
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

    public Showings getShowing(Movie movie, Screen screen, LocalDateTime time) {
        for (Showings s : showings) {
            if (s.getMovie().equals(movie) &&
                    s.getScreen().equals(screen) &&
                    s.getTime().equals(time)) {
                return s;
            }
        }
        return null;
    }


    public void addShowing(Movie movie, LocalDateTime time, Screen screen) {

        Showings showing = new Showings(movie, time, screen);
        showings.add(showing);
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




