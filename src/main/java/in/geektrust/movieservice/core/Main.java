package in.geektrust.movieservice.core;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Created by mishra.ashish@icloud.com
 */
public class Main {

    public static String hostname;
    public static String port;
    static {
        hostname = System.getenv("HOSTNAME");
        if (hostname == null) {
            hostname = "localhost";
        }
        port = System.getenv("PORT");
        if (port == null) {
            port = "8090";
        }
    }

    public static final String BASE_URI = "http://" + hostname + ":" + port + "/";

    private static HttpServer webServer;

    public static void main(String[] args) throws IOException {

        final ResourceConfig rc = new ResourceConfig().packages("in.geektrust.movieservice");
        webServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        if (webServer.isStarted()) {
            System.out.println(String.format("Movie Service started at " + BASE_URI
                    + "\nHit <enter> to stop it...", BASE_URI));
        }

        System.in.read();

        webServer.shutdownNow();
        System.out.println("Movie service is down! ");

    }
}
