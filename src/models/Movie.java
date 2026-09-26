package models;

public class Movie {

    private String id;
    private String title;
    private int durationMinutes;
    private String description;
    private int rating;

    public Movie(String id, String title, int durationMinutes, String description, int rating){
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.description = description;
        this.rating = rating;

    }
    public String getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public String getMovieDetails(){
        return title + " (" + rating + "/10 , " + durationMinutes + ")" + description;
    }
}
