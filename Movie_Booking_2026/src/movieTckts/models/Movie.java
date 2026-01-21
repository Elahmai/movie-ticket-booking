package movieTckts.models;

public class Movie {
    private String title;
    private String genre;
    private String language;
    private int duration;
    private double rating;

    public Movie(String title, String genre, int duration, String language, double rating) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.rating = rating;
    }

    
    public int getDuration() {
        return this.duration;
    }

    
    public String getTitle() { 
        return title; 
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public double getRating() {
        return rating;
    }

    
    @Override
    public String toString() {
        return String.format("%s [%s] - %d mins (Rating: %.1f)", 
                title, genre, duration, rating);
    }
}
