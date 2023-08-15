package dgcplg.moviebooking.service;

import dgcplg.moviebooking.OpenApiGeneratorApplication;
import dgcplg.moviebooking.model.Payload;
import dgcplg.moviebooking.model.SearchPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

/**
 * <p>IT class for e2e-testing of the {@link dgcplg.moviebooking.service.MovieServiceImpl#getAllMovies(Integer) getAllMovies} service.</p>
 * Here the class decreases the standard value for property {@code dgcplg.moviebooking.movie.chunksize}, so that the query only picks up a subset of the total records from the database.
 * <br />
 * After receiving the {@link <a href="https://docs.spring.io/spring-framework/docs/6.0.11/javadoc-api/org/springframework/http/ResponseEntity.html">ResponseEntity</a>} from the server, the object is tested to make sure that:
 * <ul>
 *     <li>the response status code is 200</li>
 *     <li>the response body is not empty</li>
 *     <li>the JSON attributes have the correct value</li>
 *     <li>the total number of records is 5</li>
 *     <li>the number of data retrieved is equal to the chunk size set up (2)</li>
 * </ul>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OpenApiGeneratorApplication.class)
@TestPropertySource(properties = { "dgcplg.moviebooking.movie.chunksize=2" })
public class GetAllMoviesWithPagingIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllMovies_ok() {
        ResponseEntity<Payload> movieListResponseEntity = testRestTemplate.getForEntity(
                "/v1/movie?offset=2",
                Payload.class
        );
        assert movieListResponseEntity != null;
        assert movieListResponseEntity.getStatusCode().is2xxSuccessful();
        assert movieListResponseEntity.hasBody();
        assert movieListResponseEntity.getBody() instanceof SearchPayload;
        SearchPayload searchPayload = (SearchPayload) movieListResponseEntity.getBody();
        assert searchPayload.getStatus().equals(SearchPayload.StatusEnum.SUCCESS);
        assert searchPayload.getCount() != null && searchPayload.getCount() == 5;
        assert searchPayload.getType().equals(SearchPayload.TypeEnum.MOVIE);
        assert searchPayload.getData() != null && searchPayload.getData().size() == 2;
    }
}