package de.groupsethero.backend.service;
import de.groupsethero.backend.exceptions.GeolocationInsertException;
import de.groupsethero.backend.exceptions.GeolocationRetrievalException;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.repository.GeolocationRepo;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class GeolocationServiceUnitTest {

    // Global Instances for Service-Test-Purposes
    GeolocationRepo geolocationRepo = mock(GeolocationRepo.class);
    GeolocationService geolocationService = new GeolocationService(geolocationRepo);



    // GET ALL
    @Test
    void getAllGeolocationsGivenEmptyDB_expectEmptyList() throws GeolocationRetrievalException {

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
    void getAllGeolocationsGivenSingleEntry_expectListOfOneElement() throws GeolocationRetrievalException {

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
    void getAllGeolocationsGivenMultipleEntries_expectListOfMultipleElements() throws GeolocationRetrievalException {

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
    void getGeolocationByIdGivenValidId_expectOneValidReturnObject() throws NoSuchElementException {

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
    void getGeolocationByIdGivenInvalidId_expectNoSuchElementException() throws NoSuchElementException {

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



    // CREATE
    @Test
    void createGeolocationGivenSingleObject_expectValidReturnObject() throws GeolocationInsertException {

        //GIVEN
        double inputLatitude = 47.3;
        double inputLongitude = 5.9;
        double inputElevation = 238.71;
        Geolocation resultingGeolocation = new Geolocation(47.3, 5.9, 238.71);

        when(geolocationRepo.save(new Geolocation(inputLatitude, inputLongitude, inputElevation)))
                .thenReturn(resultingGeolocation);

        //WHEN
        Geolocation actual = geolocationService.createGeolocation(new Geolocation(inputLatitude, inputLongitude, inputElevation));

        //THEN
        Geolocation expected = new Geolocation(inputLatitude, inputLongitude, inputElevation);
        verify(geolocationRepo).save(new Geolocation(inputLatitude, inputLongitude, inputElevation));
        assertEquals(expected, actual);
    }

    @Test
    void createGeolocationGivenUnsuccessfulInsertOperation_expectGeolocationInsertException() throws GeolocationInsertException {
        // GIVEN
        double inputLatitude = 47.3;
        double inputLongitude = 5.9;
        double inputElevation = 238.71;
        when(geolocationRepo.save(new Geolocation(inputLatitude, inputLongitude, inputElevation)))
                .thenThrow(new GeolocationInsertException("We're sorry - The object cannot not be created at this time."));

        try {
            // WHEN
            geolocationService.createGeolocation(new Geolocation(inputLatitude, inputLongitude, inputElevation));
            fail("Adjust this test! Exception has not been triggered");

        } catch (GeolocationInsertException e) {
            // THEN
            verify(geolocationRepo).save(new Geolocation(inputLatitude, inputLongitude, inputElevation));
            assertEquals("We're sorry - The object cannot not be created at this time.", e.getMessage());
        }
    }

}