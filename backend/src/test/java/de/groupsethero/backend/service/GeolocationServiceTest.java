package de.groupsethero.backend.service;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.repository.GeolocationRepo;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class GeolocationServiceTest {

    // Global Instances for Service-Test-Purposes
    GeolocationRepo geolocationRepo = mock(GeolocationRepo.class);
    GeolocationService geolocationService = new GeolocationService(geolocationRepo);


    @Test
    void getAllGeolocationsGivenNoEntries_expectEmptyList() throws Exception{

        //GIVEN
        List<Geolocation> geolocationList = List.of();
        when(geolocationRepo.findAll()).thenReturn(geolocationList);

        //WHEN
        List<Geolocation> actual = geolocationService.getAllGeolocations();

        //THEN
        List<Geolocation> expected = List.of();
        verify(geolocationRepo).findAll();
        assertEquals(expected, actual);
    }



/*    @Test
    void getAllMovies_expectOneMovie() {

        //GIVEN
        Movie m1 = new Movie("adalij039q", "LOTR - die Gefährten", "Peter Jackson");
        List<Movie> movieList = List.of(m1);

        //WHEN
        when(movieRepo.findAll()).thenReturn(movieList);
        List<Movie> actual = movieService.getAllMovies();

        //THEN
        List<Movie> expected = List.of(new Movie("adalij039q", "LOTR - die Gefährten", "Peter Jackson"));

        verify(movieRepo).findAll();
        assertEquals(expected, actual);
    }*/


/*    @Test
    void getGeolocationById_expect() {

        //GIVEN


        //WHEN


        //THEN

    }*/


/*    @Test
    void createGeolocation_expect() {
        //GIVEN


        //WHEN


        //THEN

    }*/
}