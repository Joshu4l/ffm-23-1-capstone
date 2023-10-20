package de.groupsethero.backend.service;
import de.groupsethero.backend.GeolocationException;
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
    void getAllGeolocationsGivenEmptyDB_expectEmptyList() throws Exception {

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

    @Test
    void getAllGeolocationsGivenSingleEntry_expectListOfOneElement() throws Exception {

        //GIVEN
        Geolocation singleEntry = new Geolocation(47.3, 5.9, 238.71);
        List<Geolocation> geolocationList = List.of(singleEntry);
        when(geolocationRepo.findAll()).thenReturn(geolocationList);

        //WHEN
        List<Geolocation> actual = geolocationService.getAllGeolocations();

        //THEN
        List<Geolocation> expected = List.of(new Geolocation(47.3, 5.9, 238.71));
        verify(geolocationRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getAllGeolocationsGivenMultipleEntries_expectListOfMultipleElements() throws Exception {

        //GIVEN
        Geolocation entry1 = new Geolocation(47.3, 5.9, 238.71);
        Geolocation entry2 = new Geolocation(47.3, 5.91, 240.03);
        Geolocation entry3 = new Geolocation(47.3, 5.92, 239.68);
        List<Geolocation> geolocationList = List.of(entry1, entry2, entry3);
        when(geolocationRepo.findAll()).thenReturn(geolocationList);

        //WHEN
        List<Geolocation> actual = geolocationService.getAllGeolocations();

        //THEN
        List<Geolocation> expected = List.of(
                new Geolocation(47.3, 5.9, 238.71),
                new Geolocation(47.3, 5.91, 240.03),
                new Geolocation(47.3, 5.92, 239.68)
                );
        verify(geolocationRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getAllGeolocations_expectExceptionCase() throws Exception {

        /* Setting up DB-entries not applicable in this scenario */

        // GIVEN
        doThrow(new GeolocationException("GeolocationException: .findAll() -operation could not be performed successfully."))
                .when(geolocationRepo).findAll();

        try {
            //WHEN
            geolocationService.getAllGeolocations();
            fail("Adjust this test! Exception has not been triggered");

        } catch (GeolocationException e) {
            //THEN
            verify(geolocationRepo).findAll();
            assertEquals("GeolocationException: .findAll() -operation could not be performed successfully.", e.getMessage());
        }
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