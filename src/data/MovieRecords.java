package data;

import models.Movie;
import models.Screen;
import models.Showings;
import models.Booking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PipedReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;




//-----------------------------------------------------------------------------------------------------

public class MovieRecords {

    private static final  String MOVIES_FILE = "movies.json";
    private static final String SCREENS_FILE = "screens.json";
    private static final String SHOWINGS_FILE = "showings.json";
    private static final String BOOKINGS_FILE = "bookings.json";

//-----------------------------------------------------------------------------------------------------

    private List<Booking> bookings = new ArrayList<>();
    private List<Showings> showings = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private List<Screen> screens = new ArrayList<>();
    private static final DateTimeFormatter SHOWING_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

//-----------------------------------------------------------------------------------------------------

    public void addMovies(Movie movie) {
        if (checkIds(movie.getId())) {
            System.out.println("Error: Movie Id already exists.");
            return;
        }
        movies.add(movie);
        System.out.println("Movie " + '"' + movie.getTitle() + '"' + " has been added!");
    }

    public Movie getMovieById(String id) {
        for (Movie m : movies) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    public void removeMoviesById(String id) {
        Movie m = getMovieById(id);
        if (m == null) {
            throw new IllegalArgumentException("Error: Movie id " + id + " does not exist.");
        }

        // Remove showings for this movie (by ID, not object)
        showings.removeIf(s -> s.getMovie().getId().equalsIgnoreCase(id));

        // Remove bookings for this movie (by ID)
        bookings.removeIf(b -> b.getShowing().getMovie().getId().equalsIgnoreCase(id));

        movies.remove(m);
        System.out.println(m.getTitle() + " has been removed.");
    }


    /*  public void removeMovies(Movie movie){
          movies.remove(movie);
      }*/
    public boolean checkIds(String id) {
        for (Movie m : movies) {
            if (m.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Movie getMovieByTitle(String title){
        String cleaned = title.trim();
        for (Movie m : movies){
            if (m.getTitle().trim().equalsIgnoreCase(cleaned))
                return m;
        }
        return null;
    }

//-----------------------------------------------------------------------------------------------------

    public List<Screen> getScreens() {
        return screens;
    }

    public void addScreen(Screen screen) {

        if (checkScreenNumber(screen.getNumber())) {
            System.out.println("Error: Screen " + screen.getNumber() + " Id already exists.");
            return;
        }
        screens.add(screen);
        System.out.println("Movie " + '"' + screen.getNumber() + '"' + " has been added!");
    }

    public Screen getScreenNumber(int number) {
        for (Screen s : screens) {
            if (s.getNumber() == number) {
                return s;
            }
        }
        return null;
    }

    public boolean checkScreenNumber(int number) {
        for (Screen s : screens) {
            if (s.getNumber() == number) {
                return true;
            }
        }
        return false;
    }

    public void removeScreen(int number) {
        Screen screenToRemove = getScreenNumber(number);

        if (screenToRemove == null) {
            throw new IllegalArgumentException("Error: Screen: " + number + " does not exist.");
        }
        screens.remove(screenToRemove);
        System.out.println("Screen number: " + number + " removed!");
    }

//-----------------------------------------------------------------------------------------------------
public Showings findShowing(String title, int screenNumber, LocalDateTime time) {
    Movie movie = getMovieByTitle(title.trim());
    Screen screen = getScreenNumber(screenNumber);

    if (movie == null || screen == null) return null;

    for (Showings s : showings) {
        LocalDateTime t = s.getTime();

        boolean sameTime =
                t.getYear() == time.getYear() &&
                        t.getMonthValue() == time.getMonthValue() &&
                        t.getDayOfMonth() == time.getDayOfMonth() &&
                        t.getHour() == time.getHour() &&
                        t.getMinute() == time.getMinute();

        if (sameTime &&
                s.getMovie().getId().equalsIgnoreCase(movie.getId()) &&
                s.getScreen().getNumber() == screenNumber) {

            return s;
        }
    }

    return null;
}


    public Showings getShowing(Movie movie, Screen screen, LocalDateTime time) {
        for (Showings s : showings) {
            if (s.getMovie().equals(movie) &&
                    s.getScreen().equals(screen) &&
                    s.getTime().isEqual(time)){
                return s;
            }
        }
        return null;
    }

    public List<Showings> getShowings(){
        return showings;
    }
    public void listShowingsByMovieTitle(String title){
        Movie movie = getMovieByTitle(title);
        if (movie == null){
            System.out.println("Error: " + title + " does not exist.");
            return;
        }
        listShowingsForMovie(movie.getId());
    }

    public void listShowingsByDate() {
        if (showings.isEmpty()) {
            System.out.println("No showings available.");
            return;
        }

        showings.sort((s1, s2) -> s1.getTime().compareTo(s2.getTime()));
        System.out.println("\n=== Current Showings ===");

        for (Showings s : showings){
            System.out.println(s.getMovie().getTitle() + " | Screen " + s.getScreen().getNumber() +
                    " | " + s.getTime().format(SHOWING_FORMAT));
        }
    }

    public void listShowingsByMovie() {
        if (showings.isEmpty()) {
            System.out.println("No showings available.");
            return;
        }
        // Sort by movie title, then by time
        showings.sort((s1, s2) -> {
            int titleCompare = s1.getMovie().getTitle().compareToIgnoreCase(s2.getMovie().getTitle());
            if (titleCompare != 0) return titleCompare;
            return s1.getTime().compareTo(s2.getTime());
        });

        System.out.println("\n=== Showings Grouped by Movie ===");

        String currentMovie = "";

        for (Showings s : showings) {
            String movieTitle = s.getMovie().getTitle();

            if (!movieTitle.equals(currentMovie)) {
                currentMovie = movieTitle;
                System.out.println("\n" + currentMovie);
                System.out.println("---------------------------");
            }

            System.out.println(
                    "Screen " + s.getScreen().getNumber() +
                            " | " + s.getTime().format(SHOWING_FORMAT)
            );
        }
    }

    public void listShowingsForMovie(String movieId) {
        Movie movie = getMovieById(movieId);

        if (movie == null) {
            System.out.println("Error: Movie ID " + movieId + " does not exist.");
            return;
        }

        List<Showings> result = new ArrayList<>();

        for (Showings s : showings) {
            if (s.getMovie().equals(movie)) {
                result.add(s);
            }
        }

        if (result.isEmpty()) {
            System.out.println("No showings found for " + movie.getTitle());
            return;
        }

        result.sort((s1, s2) -> s1.getTime().compareTo(s2.getTime()));

        System.out.println("\n=== Showings for " + movie.getTitle() + " ===");

        for (Showings s : result) {
            System.out.println(
                    "Screen " + s.getScreen().getNumber() +
                            " | " + s.getTime().format(SHOWING_FORMAT)
            );
        }
    }


    public void addShowing(Movie movie, LocalDateTime time, Screen screen) {

        if (time.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Error: Cannot add a showing in the past.");
        }

        LocalDateTime newStart = time;
        LocalDateTime newEnd = time.plusMinutes(movie.getDurationMinutes());

        // Check for overlapping showings in the same screen at the same time
        for (Showings s : showings) {
            if (s.getScreen().equals(screen)) {

                LocalDateTime existingStart = s.getTime();
                LocalDateTime existingEnd = existingStart.plusMinutes(s.getMovie().getDurationMinutes());

                boolean overlaps = newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);

                if (overlaps) {
                    throw new IllegalArgumentException(
                            "Error: Screen: " + screen.getNumber() +
                                    " is busy until " + existingEnd.format(SHOWING_FORMAT) +
                                    " due to showing of " + s.getMovie().getTitle()
                    );
                }
            }
        }
        Showings showing = new Showings(movie, time, screen);
        showings.add(showing);

        System.out.println("Showing for " + movie.getTitle() +
                " at " + time.format(SHOWING_FORMAT) + " has been added."
        );
    }

    public void removeShowing(Movie movie, LocalDateTime time, Screen screen) {
        Showings showingToRemove = getShowing(movie, screen, time);

        if (showingToRemove == null) {
            throw new IllegalArgumentException(
                    "Error: Showing for " + movie.getTitle() + " at " + time.format(SHOWING_FORMAT) + " in Screen: " + screen.getNumber() + " was not found."
            );
        }
        showings.remove(showingToRemove);
        System.out.println("Showing for " + movie.getTitle() + " at " + time.format(SHOWING_FORMAT) + " in Screen: " + screen.getNumber() + " has been removed.");
    }

//-----------------------------------------------------------------------------------------------------

    public Booking findBooking(String name, Showings showings, int row, int col){
        for (Booking b : bookings){
            if (b.getCustomerName().equalsIgnoreCase(name) && b.getShowing().equals(showings) && b.getRow() == row && b.getCol() == col){
                return b;
            }
        }
        return null;
    }


    public void addBooking(Booking b){
        bookings.add(b);
    }

    public List<Booking> getBookingsForCustomer(String name) {
        return bookings.stream()
                .filter(b -> b.getCustomerName().equalsIgnoreCase(name))
                .toList();
    }

    public void removeBooking(Booking b) {
        bookings.remove(b);
    }
//---------------------------------------------------------------------------------------------------------

    private final JsonSerializer<LocalDateTime> localDateTimeSerializer =
            (src, typeOfSrc, context) ->
                    new com.google.gson.JsonPrimitive(src.format(SHOWING_FORMAT));

    private final JsonDeserializer<LocalDateTime> localDateTimeJsonDeserializer =
            (json, typeOfT, context) ->
                    LocalDateTime.parse(json.getAsString(), SHOWING_FORMAT);


    public void saveData() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, localDateTimeSerializer)   // ✔ FIXED
                .setPrettyPrinting()
                .create();

        saveToFile(MOVIES_FILE, movies, gson);
        saveToFile(SCREENS_FILE, screens, gson);
        saveToFile(SHOWINGS_FILE, showings, gson);
        saveToFile(BOOKINGS_FILE, bookings, gson);
    }


    private <T> void saveToFile(String filename, T data, Gson gson) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(data, writer);
        } catch (Exception e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }

    public void loadData() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, localDateTimeJsonDeserializer)
                .create();

        movies = loadFromFile(MOVIES_FILE, new TypeToken<List<Movie>>(){}.getType(), gson);
        screens = loadFromFile(SCREENS_FILE, new TypeToken<List<Screen>>(){}.getType(), gson);
        showings = loadFromFile(SHOWINGS_FILE, new TypeToken<List<Showings>>(){}.getType(), gson);
        bookings = loadFromFile(BOOKINGS_FILE, new TypeToken<List<Booking>>(){}.getType(), gson);
    }


    private <T> List<T> loadFromFile(String filename, Type type, Gson gson) {
            try (FileReader reader = new FileReader(filename)) {
                return gson.fromJson(reader, type);
            } catch (Exception e) {
                return new ArrayList<>(); // file missing or empty
            }
        }

    public void removeInvalidShowings() {

        // Remove showings whose movie ID no longer exists in movies list
        showings.removeIf(s -> getMovieById(s.getMovie().getId()) == null);

        // Remove bookings for those removed showings
        bookings.removeIf(b -> getMovieById(b.getShowing().getMovie().getId()) == null);

        saveData();
    }


//---------------------------------------------------------------------------------------------------------
}




