package in.geektrust.movieservice.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import in.geektrust.movieservice.exception.InvalidInputException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import static org.junit.Assert.assertTrue;


/**
 * Created by mishra.ashish@icloud.com
 */
public class MovieRecommendationServiceTest {

    public static final String BASE_URI = "http://localhost:8090/";
    HttpServer webServer;

    @Before
    public void setUp() {
        final ResourceConfig rc = new ResourceConfig().packages("in.geektrust.movieservice");
        webServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        if (webServer.isStarted()) {
            System.out.println(String.format("Movie Servicestarted at " + BASE_URI));

        }
    }

    @Test
    public void searchMoviesBySingleText() throws Exception {

        ClientResponse response = searchMovies("102", "Johnny Depp");
        System.out.println("found single text search: " + response.getEntity(String.class));
        assertTrue(response.getStatus() == 200);
    }


    @Test
    public void searchMoviesByCommaSeparatedText() throws Exception {

        ClientResponse response = searchMovies("102", "Daryl Sabara, Polly Walker");
        System.out.println("found comma separated text search: " + response.getEntity(String.class));
        assertTrue(response.getStatus() == 200);


        response = searchMovies("104", "Gary Ross, Donald Watkins");
        System.out.println("found comma separated text search: " + response.getEntity(String.class));
        assertTrue(response.getStatus() == 200);
    }


    @Test
    public void searchMoviesNegative() throws Exception {

        ClientResponse response = searchMovies("825", "Johnny Depp");
        assertTrue(response.getStatus() == 400);
    }


    @Test
    public void getAllUserRecommendations() throws Exception {

        ClientResponse response = getMoviesForAllUsers();
        System.out.println("response: " + response.getEntity(String.class));
        assertTrue(response.getStatus() == 200);
    }


    @After
    public void tearDown() {
        webServer.shutdownNow();
        System.out.println("Movie Service is down !");
    }


    private static ClientResponse searchMovies(String userId, String searchText)
            throws UnsupportedEncodingException, InvalidInputException {

        final String URI = ""+ BASE_URI+ "movies/user/" + userId + "/search?text="+ URLEncoder.encode(searchText, "UTF-8");
        return makeGetCall(URI);
    }


    private static ClientResponse getMoviesForAllUsers() {

        final String URI = BASE_URI + "movies/users";
        return makeGetCall(URI);
    }


    private static ClientResponse  makeGetCall(final String URI) {

        System.out.println("Making GET call to " + URI);
        final WebResource service = Client.create(new DefaultClientConfig()).resource(URI);
        return service.accept("application/json").get(ClientResponse.class);
    }


}