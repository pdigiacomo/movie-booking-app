package dgcplg.moviebooking.service;

import dgcplg.moviebooking.OpenApiGeneratorApplication;
import dgcplg.moviebooking.model.MovieList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

/**
 * <p>IT class for e2e-testing of the {@link dgcplg.moviebooking.service.MovieServiceImpl#getAllMovies(Integer) getAllMovies} service.</p>
 * Here the class overrides the standard values for properties {@code dgcplg.moviebooking.movie.recordlimit} and {@code dgcplg.moviebooking.movie.chunksize}, so that they trigger paging with a very low amount of data fetched from the database.
 * <br />
 * After receiving the ResponseEntity from the server, the object is tested to make sure:
 * <ul>
 *     <li>the response status code is 200</li>
 *     <li>the response body is not empty</li>
 *     <li>the JSON attributes have the correct value</li>
 *     <li>the total number of records is 5</li>
 *     <li>the number of data retrieved is equal to the chunk size set up</li>
 * </ul>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OpenApiGeneratorApplication.class)
@TestPropertySource(properties = { "dgcplg.moviebooking.movie.recordlimit=4", "dgcplg.moviebooking.movie.chunksize=2" })
public class GetAllMoviesWithPagingIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllMovies_ok() {
        ResponseEntity<MovieList> movieListEntity = testRestTemplate.getForEntity(
                "/v1/movie?offset=1",
                MovieList.class
        );
        assert movieListEntity != null;
        assert movieListEntity.getStatusCode().is2xxSuccessful();
        assert movieListEntity.getBody() != null;
        MovieList movieList = movieListEntity.getBody();
        assert movieList.getStatus().getValue().equals("success");
        assert movieList.getCount() == 5;
        assert movieList.getData().size() == 2;
        assert movieList.getType().equals("Movie");
    }
}