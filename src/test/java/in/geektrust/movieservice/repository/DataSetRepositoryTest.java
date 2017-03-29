package in.geektrust.movieservice.repository;

import java.util.List;

/**
 * Created by mishra.ashish@icloud.com
 */
public class DataSetRepositoryTest {


    @org.junit.Test
    public void initTest() throws Exception {

        DataSetRepository repo = DataSetRepository.getInstance();
        //repo.init();
    }

    @org.junit.Test
    public void dataTest() throws Exception {

        DataSetRepository.getInstance().init();
        List<String> movies = DataSetRepository.getInstance().findMovies("105", "Tom Hanks");
        System.out.println(movies.size() + " movies : " + movies);
        movies = DataSetRepository.getInstance().findMovies("102", "Johnny Depp");
        System.out.println(movies.size() + " movies : " +  movies);

        System.out.println(DataSetRepository.getInstance().getRecommendedMoviesForAllUsers());
    }

}