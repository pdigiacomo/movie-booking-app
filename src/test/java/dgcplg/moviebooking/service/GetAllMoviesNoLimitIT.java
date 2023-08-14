package dgcplg.moviebooking.service;

import dgcplg.moviebooking.OpenApiGeneratorApplication;
import dgcplg.moviebooking.model.MovieList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

/**
 * <p>IT class for e2e-testing of the {@link dgcplg.moviebooking.service.MovieServiceImpl#getAllMovies(Integer) getAllMovies} service.</p>
 * Here the class invokes the service method retrieving all of the records in the database, without hitting the server's limit.
 * <br />
 * After receiving the {@link <a href="https://docs.spring.io/spring-framework/docs/6.0.11/javadoc-api/org/springframework/http/ResponseEntity.html">ResponseEntity</a>} from the server, the object is tested to make sure that:
 * <ul>
 *     <li>the response status code is 200</li>
 *     <li>the response body is not empty</li>
 *     <li>the JSON attributes have the correct value</li>
 *     <li>the total number of records is 5</li>
 *     <li>the number of fetched data is 5</li>
 * </ul>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OpenApiGeneratorApplication.class)
public class GetAllMoviesNoLimitIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllMovies_ok_noRecordLimit() {
        ResponseEntity<MovieList> movieListEntity = testRestTemplate.getForEntity(
                "/v1/movie",
                MovieList.class
        );
        assert movieListEntity != null;
        assert movieListEntity.getStatusCode().is2xxSuccessful();
        assert movieListEntity.getBody() != null;
        MovieList movieList = movieListEntity.getBody();
        assert movieList.getStatus().getValue().equals("success");
        assert movieList.getCount() == 5;
        assert movieList.getData().size() == 5;
        assert movieList.getType().equals("Movie");
    }
}