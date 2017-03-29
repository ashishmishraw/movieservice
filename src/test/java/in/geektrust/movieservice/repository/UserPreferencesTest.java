package in.geektrust.movieservice.repository;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by mishra.ashish@icloud.com
 */
public class UserPreferencesTest {


    @Test
    public void testUserPreferences() throws Exception {

        UserPreferences.Preference pref = UserPreferences.getUserPreferences("101");

        assertTrue(pref.getDirectors().contains("Quentin Tarantino"));
        assertTrue(pref.getActors().contains("Anne Hathaway"));
        assertTrue(pref.getLanguages().toString().equals("[English]"));


        pref = UserPreferences.getUserPreferences("106");
        assertTrue(pref.getLanguages().contains("Spanish"));
        assertTrue(pref.getActors().contains("Johnny Depp"));

    }


    @Test
    public void testUserPreferencesNegative() throws Exception {

        UserPreferences.Preference pref = UserPreferences.getUserPreferences("909");
        assertNull(pref);
    }

}