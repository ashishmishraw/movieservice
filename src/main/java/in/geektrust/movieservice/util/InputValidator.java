package in.geektrust.movieservice.util;

import in.geektrust.movieservice.exception.InvalidInputException;
import in.geektrust.movieservice.repository.UserPreferences;

/**
 * Created by mishra.ashish@icloud.com
 */
public class InputValidator {

    public static void validateInputs(String userId, String text) throws InvalidInputException {

        if ( !UserPreferences.getAllUserPreferences().keySet().contains(userId)) {
            throw new InvalidInputException("userId", "userId does not exist!");
        }
        if ( null == text || text.isEmpty()) {
            throw new InvalidInputException("userId", "search text must not be empty");
        }
    }
}
