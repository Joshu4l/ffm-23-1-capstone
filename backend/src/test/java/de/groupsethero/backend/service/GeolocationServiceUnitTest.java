package de.groupsethero.backend.service;
import de.groupsethero.backend.exceptions.RadiusInKmTooSmallException;
import de.groupsethero.backend.exceptions.GeolocationRetrievalException;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.repository.GeolocationRepo;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class GeolocationServiceUnitTest {

    // Global Instances for Service-Test-Purposes
    GeolocationRepo geolocationRepo = mock(GeolocationRepo.class);
    GeolocationService geolocationService = new GeolocationService(geolocationRepo);


    // GET ALL
    @Test
    void getAllGeolocations_givenEmptyDB_expectEmptyList() throws GeolocationRetrievalException {

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
    void getAllGeolocations_givenSingleEntry_expectListOfOneElement() throws GeolocationRetrievalException {

        //GIVEN
        Geolocation singleEntry = new Geolocation("validId", 47.3, 5.9, 238.71);
        List<Geolocation> geolocationList = List.of(singleEntry);
        when(geolocationRepo.findAll()).thenReturn(geolocationList);

        //WHEN
        List<Geolocation> actual = geolocationService.getAllGeolocations();

        //THEN
        List<Geolocation> expected = List.of(new Geolocation("validId", 47.3, 5.9, 238.71));
        verify(geolocationRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getAllGeolocations_givenMultipleEntries_expectListOfMultipleElements() throws GeolocationRetrievalException {

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
    void getAllGeolocations_expectGeolocationRetrievalException() throws GeolocationRetrievalException {

        /* Setting up DB-entries not applicable in this scenario */

        // GIVEN
        doThrow(new GeolocationRetrievalException("GeolocationException: .findAll() -operation could not be performed successfully."))
                .when(geolocationRepo).findAll();

        try {
            //WHEN
            geolocationService.getAllGeolocations();
            fail("Adjust this test! Exception has not been triggered");

        } catch (GeolocationRetrievalException e) {
            //THEN
            verify(geolocationRepo).findAll();
            assertEquals("GeolocationException: .findAll() -operation could not be performed successfully.", e.getMessage());
        }
    }



    // GET BY ID
    @Test
    void getGeolocationById_givenValidId_expectOneValidReturnObject() throws NoSuchElementException {

        // GIVEN
        String validId = "653063420c470e5631cc4dba";
        Geolocation singleSavedObject = new Geolocation(validId, 47.3, 5.9, 238.71);

        when(geolocationRepo.findById(validId))
                .thenReturn(Optional.of(singleSavedObject));

        // WHEN
        Geolocation actual = geolocationService.getGeolocationById(validId);

        // THEN
        Geolocation expected = new Geolocation("653063420c470e5631cc4dba", 47.3, 5.9, 238.71);
        verify(geolocationRepo).findById(validId);
        assertEquals(expected, actual);
    }

    @Test
    void getGeolocationById_givenInvalidId_expectNoSuchElementException() throws NoSuchElementException {

        // GIVEN
        String invalidId = "nonsenseId";

        when(geolocationRepo.findById(invalidId))
                .thenThrow(new NoSuchElementException("Nothing here - The geolocation specified doesn't seem to exist"));

        try {
            // WHEN
            geolocationService.getGeolocationById(invalidId);
            fail("Adjust this test! Exception has not been triggered");

        } catch (NoSuchElementException e) {
            // THEN
            verify(geolocationRepo).findById(invalidId);
            assertEquals("Nothing here - The geolocation specified doesn't seem to exist", e.getMessage());
        }
    }



    // TODO: defineQueryBoundaries




    // DB COLLECTION SUBSET
    @Test
    void getGeolocationSubsetToBeQueried_givenExistingLatAndLngBoundaries_expectSubsetOfFourObjects() {

        // GIVEN
        double lowerLatitudeBoundary = 47.3;
        double upperLatitudeBoundary = 47.31;
        double lowerLongitudeBoundary = 5.9;
        double upperLongitudeBoundary = 5.91;

        List<Geolocation> resultSubset =  List.of(
                new Geolocation(47.3, 5.9, 238.71),
                new Geolocation(47.3, 5.91, 240.03),
                new Geolocation(47.31, 5.9, 216.56),
                new Geolocation(47.31, 5.91, 218.76)
        );
        when(geolocationRepo.findByLatitudeBetweenAndLongitudeBetween(
                lowerLatitudeBoundary, upperLatitudeBoundary, lowerLongitudeBoundary, upperLongitudeBoundary
                )
            ).thenReturn(resultSubset);


        // WHEN
        List<Geolocation> actual = geolocationService.getGeolocationSubsetToBeQueried(
                lowerLatitudeBoundary, upperLatitudeBoundary, lowerLongitudeBoundary, upperLongitudeBoundary
        );


        // THEN
        List<Geolocation> expected = List.of(
                new Geolocation(47.3, 5.9, 238.71),
                new Geolocation(47.3, 5.91, 240.03),
                new Geolocation(47.31, 5.9, 216.56),
                new Geolocation(47.31, 5.91, 218.76)
        );
        verify(geolocationRepo).findByLatitudeBetweenAndLongitudeBetween(
                47.3, 47.31, 5.9, 5.91
        );
        assertEquals(expected, actual);
        assertEquals(4, actual.size());
    }

    @Test
    void getGeolocationSubsetToBeQueried_givenNonexistentLatAndLngValues_expectSubsetOfZeroObjects() {

        // GIVEN
        double lowerLatitudeBoundary = 50.00;
        double upperLatitudeBoundary = 50.00;
        double lowerLongitudeBoundary = 50.00;
        double upperLongitudeBoundary = 50.00;

        List<Geolocation> resultSubset =  List.of(); // empty list
        when(geolocationRepo.findByLatitudeBetweenAndLongitudeBetween(
                lowerLatitudeBoundary, upperLatitudeBoundary, lowerLongitudeBoundary, upperLongitudeBoundary
                )
            ).thenReturn(resultSubset);


        // WHEN
        List<Geolocation> actual = geolocationService.getGeolocationSubsetToBeQueried(
                lowerLatitudeBoundary, upperLatitudeBoundary, lowerLongitudeBoundary, upperLongitudeBoundary
        );


        // THEN
        List<Geolocation> expected = List.of();
        verify(geolocationRepo).findByLatitudeBetweenAndLongitudeBetween(
                50.00, 50.00, 50.00, 50.00
        );
        assertEquals(expected, actual);
        assertEquals(0, actual.size());
    }

    @Test
    void getGeolocationSubsetToBeQueried_givenExceptionThrown_expectGeolocationRetrievalException() throws GeolocationRetrievalException {
        // GIVEN
        double lowerLatitudeBoundary = 47.3;
        double upperLatitudeBoundary = 47.31;
        double lowerLongitudeBoundary = 5.9;
        double upperLongitudeBoundary = 5.91;

        when(geolocationRepo.findByLatitudeBetweenAndLongitudeBetween(
                lowerLatitudeBoundary, upperLatitudeBoundary, lowerLongitudeBoundary, upperLongitudeBoundary
        )).thenThrow(new GeolocationRetrievalException("GeolocationRetrievalException: database operation could not be performed successfully."));


        // WHEN & THEN
        assertThrows(
            GeolocationRetrievalException.class, () -> geolocationService.getGeolocationSubsetToBeQueried(
                lowerLatitudeBoundary, upperLatitudeBoundary, lowerLongitudeBoundary, upperLongitudeBoundary
            )
        );

    }



    // HAVERSINE DISTANCE
    @Test
    void haversineDistance_givenValidLatAndLng_expectValidDistanceValue () throws IllegalArgumentException {
        // GIVEN
        double coordinate1Lat = 52.34;
        double coordinate1Lng = 10.0;

        double coordinate2Lat = 52.34;
        double coordinate2Lng = 10.01;


        // WHEN
        double actual = geolocationService.haversineDistance(
                coordinate1Lat, coordinate1Lng,
                coordinate2Lat, coordinate2Lng
        );


        // THEN
        double expected = 0.6793726609785211;
        assertEquals(expected, actual);
    }

    @Test
    void haversineDistance_givenOutOfBoundsLatAndLng_expectIllegalArgumentException () throws IllegalArgumentException {
        // GIVEN
        double coordinate1Lat = 91;
        double coordinate1Lng = 181;

        double coordinate2Lat = -91;
        double coordinate2Lng = -181;


        // WHEN & THEN
        assertThrows(
            IllegalArgumentException.class, () -> geolocationService.haversineDistance(
                    coordinate1Lat, coordinate1Lng,
                    coordinate2Lat, coordinate2Lng
            )
        );
    }



    // AVERAGE ELEVATION IN PERCENT
    @Test
    void calculateAverageElevationInPercent_givenSubsetOfNineLocations_ExpectPercentageValue () throws Exception {
        // GIVEN
        List<Geolocation> geolocationSubset = List.of(
                new Geolocation(52.47, 13.4, 49.01),
                new Geolocation(52.47, 13.41, 49.02),
                new Geolocation(52.47, 13.42, 49.03),
                new Geolocation(52.48, 13.4, 49.04),
                new Geolocation(52.48, 13.41, 49.05),
                new Geolocation(52.48, 13.42, 49.06),
                new Geolocation(52.49, 13.4, 49.07),
                new Geolocation(52.49, 13.41, 49.08),
                new Geolocation(52.49, 13.42, 49.09)
        );
        // WHEN
        Double actual = geolocationService.calculateAverageElevationInPercent(geolocationSubset);

        // THEN
        Double expected = 648.112008694975;
        assertEquals(expected, actual);
    }

    @Test
    void calculateAverageElevationInPercent_givenEmptyCollectionSubset_ExpectRadiusInKmTooSmallException () throws RadiusInKmTooSmallException {
        // GIVEN
        List<Geolocation> emptySubset = List.of();

        // WHEN & THEN
        assertThrows(
            RadiusInKmTooSmallException.class, () -> geolocationService.calculateAverageElevationInPercent(
                    emptySubset
            )
        );
    }

}