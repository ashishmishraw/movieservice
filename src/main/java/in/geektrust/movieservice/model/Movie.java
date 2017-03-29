package in.geektrust.movieservice.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Model class
 *
 * Created by mishra.ashish@icloud.com
 */
public class Movie {

    private String movie_title;
    private String director_name;
    private String actor_1_name;
    private String actor_2_name;
    private String actor_3_name;
    private String language;
    private Double imdb_score;
    private Set<String> actors;


    public Movie(){}

    public Movie(String movie_title, String director_name, String actor_1_name,
                 String actor_2_name, String actor_3_name, String language, Double imdb_score ) {
        this.movie_title = movie_title;
        this.director_name = director_name;
        this.actor_1_name = actor_1_name;
        this.actor_2_name = actor_2_name;
        this.actor_3_name = actor_3_name;
        this.language = language;
        this.imdb_score = imdb_score;

        actors = new HashSet<>();
        if ( null !=actor_3_name  && !actor_3_name.isEmpty())
            actors.add(actor_3_name);
        if ( null !=actor_2_name  && !actor_2_name.isEmpty())
            actors.add(actor_2_name);
        if ( null !=actor_1_name  && !actor_1_name.isEmpty())
            actors.add(actor_1_name);
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getDirector_name() {
        return director_name;
    }

    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }

    public String getActor_1_name() {
        return actor_1_name;
    }

    public void setActor_1_name(String actor_1_name) {
        this.actor_1_name = actor_1_name;
    }

    public String getActor_2_name() {
        return actor_2_name;
    }

    public void setActor_2_name(String actor_2_name) {
        this.actor_2_name = actor_2_name;
    }

    public String getActor_3_name() {
        return actor_3_name;
    }

    public void setActor_3_name(String actor_3_name) {
        this.actor_3_name = actor_3_name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<String> getActors() {
        return this.actors;
    }


    public Double getImdb_score() {
        return imdb_score;
    }

    public void setImdb_score(Double imdb_score) {
        this.imdb_score = imdb_score;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movie_title='" + movie_title + '\'' +
                ", director_name='" + director_name + '\'' +
                ", actor_1_name='" + actor_1_name + '\'' +
                ", actor_2_name='" + actor_2_name + '\'' +
                ", actor_3_name='" + actor_3_name + '\'' +
                ", language='" + language + '\'' +
                ", imdb_score='" + imdb_score + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Movie movie = (Movie) o;

        if (!movie_title.equals(movie.movie_title))
            return false;
        if (director_name !=null ? !director_name.equals(movie.director_name) : movie.director_name != null)
            return false;
        if (actor_1_name !=null ? !actor_1_name.equals(movie.actor_1_name): movie.actor_1_name != null)
            return false;
        if (actor_2_name != null ? !actor_2_name.equals(movie.actor_2_name) : movie.actor_2_name != null)
            return false;
        if (actor_3_name != null ? !actor_3_name.equals(movie.actor_3_name) : movie.actor_3_name != null)
            return false;
        return language != null ? language.equals(movie.language) : movie.language == null;
    }

    @Override
    public int hashCode() {
        int result = movie_title.hashCode();
        result = 31 * result + (director_name != null ? director_name.hashCode(): 123);
        result = 31 * result + (actor_1_name != null? actor_1_name.hashCode(): 1);
        result = 31 * result + (actor_2_name != null ? actor_2_name.hashCode() : 0);
        result = 31 * result + (actor_3_name != null ? actor_3_name.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}
