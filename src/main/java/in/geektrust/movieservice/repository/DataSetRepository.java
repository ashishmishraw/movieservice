package in.geektrust.movieservice.repository;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import in.geektrust.movieservice.model.Movie;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * Data set repository class which is responsible for making data set available for
 * carrying out various data processing operations
 *
 * Created by mishra.ashish@icloud.com
 * @since 1.6+
 */
public class DataSetRepository {


    private static Set<Movie> cache = Collections.synchronizedSet(new HashSet<Movie>());

    private DataSetRepository() {
        init();
    }

    private static DataSetRepository repo = new DataSetRepository();

    /**
     * Singleton instance
     * @return
     */
    public static DataSetRepository getInstance () {

        if (repo == null)
            return new DataSetRepository();
        return repo;
    }


    /**
     * Initializes dataset repository
     */
    public void init() {

        String fileName = "/movie_metadata.csv";
        FileReader fileReader = null;
        URL resource = DataSetRepository.class.getResource(fileName);

        try {
            fileReader = new FileReader(resource.getFile());
            CSVReader csvReader = new CSVReader(fileReader);
            CsvToBean<Movie> csv = new CsvToBean() {
                protected Object convertValue(String value, PropertyDescriptor prop) throws InstantiationException, IllegalAccessException {
                    if (StringUtils.isEmpty(value)) {
                        value = null;
                    }
                    return super.convertValue(value, prop);
                }
            };

            //Set column mapping strategy, so that only selected columns are parsed
            List<Movie> movies = csv.parse(setColumMapping(), csvReader);
            movies.forEach(movie -> cache.add(movie));

        } catch ( FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fileReader != null)
                    fileReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * Finds list of movies matching the search text and given user preferences
     *
     * @param userId
     * @param text
     * @return List of {@link Movie}s
     */
    public List<String> findMovies(String userId, String text) {

        UserPreferences.Preference preferences = UserPreferences.getUserPreferences(userId);

        List<String> filteredMovies1 = findBasedOnPreferences(text, preferences);
        List<String> filteredMovies = findBasedOnText(text);

        if(filteredMovies1 != null && filteredMovies1.size() > 0) {
            filteredMovies1.addAll(filteredMovies);
            return filteredMovies1;
        } else {
            System.out.println("preferences based movies not found for user " + userId);
        }
        return filteredMovies;
    }


    /**
     * Finds movies from data set based on user-preferences and search-text provided
     * @param text
     * @param preferences
     * @return
     */
    private List<String> findBasedOnPreferences(String text, UserPreferences.Preference preferences) {

        List<String> searchTextList = getSearchTextAsList(text);

        if (searchTextList.size() == 1) { //in case of single text in search

            return cache.stream()
                    .filter(m -> matchesPreferences(m, preferences, text.trim()))
                    .map(Movie::getMovie_title)
                    .sorted()
                    .collect(Collectors.toList());
        } else { // in case of comma separated search texts

            List<Predicate<Movie>> filterChain = new ArrayList<>(searchTextList.size()); //create a filter chain

            searchTextList.forEach(t -> filterChain.add( m -> matchesPreferences(m, preferences, t.trim())));

            return cache.stream()
                     .filter(filterChain
                             .stream()
                             .reduce(m -> true, Predicate::and)) //apply composite filter, all text should match
                     .map(Movie::getMovie_title)
                     .sorted()
                     .collect(Collectors.toList());
        }
    }

    /**
     * Finds movies from data set based on search text provided
     * @param text
     * @return
     */
    private List<String> findBasedOnText(String text) {

        List<String> searchTextList = getSearchTextAsList(text);

        if (searchTextList.size() == 1) { //in case of single text in search
            return cache.stream()
                    .filter(m -> matchText(m, text.trim()))
                    .map(Movie::getMovie_title)
                    .sorted()
                    .collect(Collectors.toList());
        } else {   // in case of comma separated search texts

            List<Predicate<Movie>> filterChain = new ArrayList<>(searchTextList.size());

            searchTextList.forEach(t -> filterChain.add( m -> matchText(m, t.trim())));

            return cache.stream()
                    .filter(filterChain
                            .stream()
                            .reduce(m -> true, Predicate::and)) //apply composite filter, all text should match
                    .map(Movie::getMovie_title)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }


    /**
     * Provides composite condition for matching movies in data set against user-preferences and search-text provided
     * @param m - movie
     * @param pref - user preferences
     * @param text - search text
     * @return boolean
     */
    private static boolean matchesPreferences( Movie m , UserPreferences.Preference pref,String text) {

        Set<String> preferenceSet = pref.getUniqueSet();

        Stream<Boolean> matchPreferences = Stream.of(
                preferenceSet.contains(m.getActor_1_name()),
                preferenceSet.contains(m.getActor_2_name()),
                preferenceSet.contains(m.getActor_3_name()),
                preferenceSet.contains(m.getDirector_name()));

        Boolean flag =  (null == text)
                ? // match only user preferences
                matchPreferences.anyMatch(p -> p)
                        && pref.getLanguages().contains(m.getLanguage())
                : // else match user preferences and text preferences both
                matchPreferences.anyMatch(p -> p)
                        && pref.getLanguages().contains(m.getLanguage())
                        && matchText(m, text); //and match search text also

        if (flag)
            return true;

        return false;
    }


    /**
     * Provides composite condition for matching movies from data set by matching against search text provided
     * @param m - movie
     * @param text - search Text
     * @return boolean
     */
    private static boolean matchText(Movie m, String text) {

        Stream<Boolean> matchesText = Stream.of(
                text.equalsIgnoreCase(m.getMovie_title()),
                text.equalsIgnoreCase(m.getDirector_name()),
                text.equalsIgnoreCase(m.getActor_1_name()),
                text.equalsIgnoreCase(m.getActor_2_name()),
                text.equalsIgnoreCase(m.getActor_3_name()));

        if (matchesText.anyMatch(p -> p))
            return true;

        return false;
    }


    /**
     * Gets text search as list if it includes comma-separated texts
     * Returns single text in list if only one text search is used
     *
     * @param searchText
     * @return
     */
    private List<String> getSearchTextAsList(String searchText) {

        if (searchText.contains(",")) {
            return Arrays.asList(searchText.split(","));
        } else
            return Arrays.asList(searchText.trim());
    }


    /**
     * Gets recommended movie map for all users, combination of userId and sorted movie-title  list
     * @return Map
     */
    public Map<String,List<String>> getRecommendedMoviesForAllUsers() {

        return UserPreferences.getAllUserPreferences()
                .entrySet()
                .stream()
                .collect(Collectors.toMap( Map.Entry::getKey,
                        e -> getRecommendations(e.getValue()))); //get recommended titles
    }


    /**
     * Filters top movies sorted on IMDB ratings which matches preferences for each users
     * @param preference
     * @return List of movie titles
     */
    private List<String> getRecommendations( UserPreferences.Preference preference) {

        return  cache.stream()
                .filter(m -> matchesPreferences(m, preference, null)) //filter by user preference
                .sorted(Comparator.comparing(Movie::getImdb_score).reversed()) //sort based on IMDB score
                .map(Movie::getMovie_title) //map by movie title
                .limit(3) //only 3 records
                .sorted() //sort alphabetically
                .collect(Collectors.toList()); //collect as list
    }


    private static HeaderColumnNameMappingStrategy setColumMapping() {

        HeaderColumnNameMappingStrategy<Movie> strategy  = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(Movie.class);
        return strategy;
    }
}
