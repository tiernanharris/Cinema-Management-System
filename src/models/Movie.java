package models;

public class Movie {

    private String id;
    private String title;
    private int durationMinutes;
    private String description;
    private String rating;

    public Movie(String id, String title, int durationMinutes, String description, String rating){
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

    public String getRating() {
        return rating;
    }

    public String getMovieDetails(){
        return title + " (" + rating + ", " + durationMinutes + ")" + description;
    }
}
