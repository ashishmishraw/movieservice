package in.geektrust.movieservice.repository;

import in.geektrust.movieservice.model.Movie;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Spliterator class for reading specific movie properties from CSV columns
 *
 * Created by mishra.ashish@icloud.com
 */
public class MovieSpliterator implements Spliterator<Movie> {

    private final Spliterator<String> lineSpliterator;
    private String movie_title;
    private String director_name;
    private String actor_1_name;
    private String actor_2_name;
    private String actor_3_name;
    private String language;
    private String imdb_score;


    public MovieSpliterator(Spliterator<String> lineSpliterator) {

        this.lineSpliterator = lineSpliterator;
    }


    @Override
    public Spliterator<Movie> trySplit() {

        return null;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Movie> action) {

        if ( lineSpliterator.tryAdvance(line ->  readSpecificColumns(line) ) ) {

            Movie movie = null;
            try {
                movie = new Movie(URLDecoder.decode(movie_title.trim(), "UTF-8"), director_name, actor_1_name,
                            actor_2_name, actor_3_name, language, Double.parseDouble(imdb_score));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            movie.setLanguage(language);
            movie.setImdb_score(Double.parseDouble(imdb_score.isEmpty()? "0": imdb_score));

            action.accept(movie);
            return true;
        }
        return false;
    }


    private void readSpecificColumns(String line) {

        String[] columns = line.split(",");
        //TODO use CSV reader to read specific columns than splitting
        this.director_name = columns[1].trim();
        this.actor_3_name = columns[14].trim();
        this.actor_2_name = columns[6].trim();
        this.actor_1_name = columns[10].trim();

        if (columns[11].startsWith("\"")) {
            this.movie_title = columns[11] + columns[12];
            this.language = columns[20].trim();
            this.imdb_score = columns[26].trim();
        } else {
            this.movie_title = columns[11].trim();
            this.language = columns[19].trim();
            this.imdb_score = columns[25].trim();
        }
    }

    @Override
    public long estimateSize() {

        return lineSpliterator.estimateSize();
    }

    @Override
    public int characteristics() {

        return lineSpliterator.characteristics();
    }
}
