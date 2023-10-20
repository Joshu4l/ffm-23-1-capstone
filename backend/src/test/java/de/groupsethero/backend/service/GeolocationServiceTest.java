package de.groupsethero.backend.service;
import de.groupsethero.backend.GeolocationException;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.repository.GeolocationRepo;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class GeolocationServiceTest {

    // Global Instances for Service-Test-Purposes
    GeolocationRepo geolocationRepo = mock(GeolocationRepo.class);
    GeolocationService geolocationService = new GeolocationService(geolocationRepo);


    // ALL
    @Test
    void getAllGeolocationsGivenEmptyDB_expectEmptyList() throws GeolocationException {

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
    void getAllGeolocationsGivenSingleEntry_expectListOfOneElement() throws GeolocationException {

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
    void getAllGeolocationsGivenMultipleEntries_expectListOfMultipleElements() throws GeolocationException {

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
    void getAllGeolocations_expectExceptionCase() throws GeolocationException {

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



    // BY ID
    @Test
    void getGeolocationByIdGivenValidId_expectOneElement() throws NoSuchElementException {
        // GIVEN
        String validId = "653063420c470e5631cc4dba";

        Geolocation singleEntry = new Geolocation(47.3, 5.9, 238.71);
        when(geolocationRepo.findById(validId))
                .thenReturn(Optional.of(singleEntry));

        // WHEN
        Geolocation actual = geolocationService.getGeolocationById(validId);

        // THEN
        Geolocation expected = new Geolocation(47.3, 5.9, 238.71);
        verify(geolocationRepo).findById(validId);
        assertEquals(expected, actual);
    }

    @Test
    void getGeolocationByIdGivenInvalidId_expectException() throws NoSuchElementException {

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
/*    @Test
    void createGeolocation_expect() {
        //GIVEN


        //WHEN


        //THEN

    }*/
}