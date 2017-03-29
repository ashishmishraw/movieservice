package in.geektrust.movieservice.service.impl;

import com.google.gson.Gson;
import in.geektrust.movieservice.repository.DataSetRepository;
import in.geektrust.movieservice.service.ServiceBase;
import in.geektrust.movieservice.util.InputValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Rest endpoint implementation for movie recommendation service
 *
 * Created by mishra.ashish@icloud.com
 */

@Path("movies")
@Produces(MediaType.APPLICATION_JSON)
public class MovieRecommendationService extends ServiceBase {

    /**
     * Accepts a search string and userID and return movies in the order of preference for that user
     *
     * @param userId
     * @param text
     * @return JSON response
     */
    @GET
    @Path("user/{userId}/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchMovies(@PathParam("userId") String userId,
                                 @QueryParam("text") String text) {

        try {

            InputValidator.validateInputs(userId, text);
            URLDecoder.decode(text, "UTF-8");

            List<String> movies = DataSetRepository.getInstance().findMovies(userId, text);

            if (null != movies && !movies.isEmpty()) {
                return Response.ok(new Gson().toJson(movies), MediaType.APPLICATION_JSON).build();
            }

        } catch (Exception ex) {
            //ex.printStackTrace();
            return generateErrorResponse(ex, Response.Status.BAD_REQUEST, ex.getMessage());
        }
        return Response.ok("{}").build();
    }


    /**
     * Lists out all the users and the top 3 recommended movies for each user based on their preferences
     *
     * @return userids and top 3 movies recommended. The movie names are sorted in the alphabetic order of titles.
     * If there is no recommendations then empty array is returned
     */
    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        try {

            Map<String, List<String>> userMap = DataSetRepository.getInstance().getRecommendedMoviesForAllUsers();

            StringBuilder json = new StringBuilder();
            userMap.forEach( (key, value) -> {
                json.append("[{ ").append("\"user\":").append("\"" + key).append("\", \"movies\": ").append(new Gson().toJson(value)).append("},");
            });

            if (null != userMap && !userMap.isEmpty()) {
                return Response.ok(json.deleteCharAt(json.length()-1).append("]").toString(), MediaType.APPLICATION_JSON).build();
            }

        } catch (Exception ex) {
            return generateErrorResponse(ex, Response.Status.BAD_REQUEST, ex.getMessage());
        }
        return Response.ok("{}").build();
    }



}
