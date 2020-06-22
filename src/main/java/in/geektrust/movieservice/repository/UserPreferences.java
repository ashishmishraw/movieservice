package in.geektrust.movieservice.repository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

/**
 * User Preferences cache
 *
 * Created by mishra.ashish@icloud.com
 */
public class UserPreferences {

    private Map<String, Preference> data = new HashMap<>();

    private static Map<String, Preference> retMap = new HashMap<>();


    List<String> preferred_languages;
    List<String> favourite_actors;
    List<String> favourite_directors;


    static { //initializer

        String fileName = "/user_preferences.json";

        try { //(Stream<String> lines = Files.lines(Paths.get(resource.toURI()))) {

            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(new File(".").getAbsolutePath() + "/src/main/resources/" + fileName));
	    reader.setLenient(true);
            JsonObject[] elements  = gson.fromJson(reader, new TypeToken<JsonObject[]>() {}.getType());

            Stream.of(elements).forEach(e -> setDataInMap(e, gson));

        } catch (IOException e ) { //| URISyntaxException e) {
            e.printStackTrace();
        }
    }


    private static void setDataInMap(JsonObject e, Gson mapper) {

        String json = e.entrySet().toString();
        json = json.substring(1,json.length()-1);

        String[] keyValues = json.split("=");

        Preference p = mapper.fromJson(keyValues[1], Preference.class);
        retMap.put(keyValues[0], p);
    }


    /**
     * Gets user preferences as object
     *
     * @param userId
     * @return Preference object
     */
    public static Preference getUserPreferences(String userId) {

        return retMap.get(userId.toString());
    }


    /**
     * Inner class
     */
    class Preference {

        List<String> preferred_languages;
        List<String> favourite_actors;
        List<String> favourite_directors;


        Preference(String[] langs, String[] actors, String[] directors) {
            this.preferred_languages =  Arrays.asList(langs);
            this.favourite_actors =  Arrays.asList(actors);
            this.favourite_directors =  Arrays.asList(directors);
        }


        public List<String> getLanguages() {
            return preferred_languages;
        }


        public List<String> getActors() {
            return favourite_actors;
        }


        public List<String> getDirectors() {
            return favourite_directors;
        }

        public void setLanguages(String[] languages) {
            this.preferred_languages = Arrays.asList(languages);
        }

        public void setActors(String[] actors) {
            this.favourite_actors = Arrays.asList( actors);
        }

        public void setDirectors(String[] directors) {
            this.favourite_directors =  Arrays.asList(directors);
        }

        @Override
        public String toString() {
            return "Preference{" +
                    "languages=" + preferred_languages.toString() +
                    ", actors=" + favourite_actors.toString() +
                    ", directors=" + favourite_directors.toString() +
                    '}';
        }

        /**
         * Get preferences as a Set
         * @return
         */
        public Set<String> getUniqueSet() {

            Set<String> prefSet = new HashSet<>();
            prefSet.addAll(preferred_languages);
            prefSet.addAll(favourite_actors);
            prefSet.addAll(favourite_directors);
            return prefSet;
        }
    }

    /**
     * Returns map of all userIds as keys and corresponding preferences as values
     *
     * @return
     */
    public static Map<String, Preference> getAllUserPreferences() {
        return retMap;
    }
}
