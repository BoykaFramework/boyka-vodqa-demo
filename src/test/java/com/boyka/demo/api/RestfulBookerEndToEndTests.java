package com.boyka.demo.api;

import static com.boyka.demo.api.data.BookingRequestData.getBookingData;
import static com.boyka.demo.api.requests.BookingRequest.createBooking;
import static com.boyka.demo.api.requests.BookingRequest.deleteBooking;
import static com.boyka.demo.api.requests.BookingRequest.getBooking;
import static io.github.boykaframework.actions.api.ApiActions.withRequest;
import static io.github.boykaframework.enums.PlatformType.API;
import static io.github.boykaframework.manager.ParallelSession.clearSession;
import static io.github.boykaframework.manager.ParallelSession.createSession;
import static io.github.boykaframework.manager.ParallelSession.getSession;

import com.boyka.demo.api.data.BookingRequestData;
import com.boyka.demo.api.pojo.BookingData;
import com.boyka.demo.api.requests.BookingRequest;
import io.github.boykaframework.exception.FrameworkError;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestfulBookerEndToEndTests {
    private static final String BOOKING_ID = "bookingId";

    private BookingData newBooking;

    @BeforeClass (description = "Setup test class")
    public void setupTestClass () {
        createSession (API, "test_restfulbooker");
        this.newBooking = getBookingData ();
    }

    @AfterClass (description = "Tear down test class")
    public void tearDownTestClass () {
        clearSession ();
    }

    @Test (description = "Test for creating new booking with POST request")
    public void testCreateBooking () {
        final var request = createBooking (this.newBooking);
        final var response = withRequest (request).execute ();

        response.verifyStatusCode ()
            .isEqualTo (200);
        response.verifyStatusMessage ()
            .isEqualTo ("OK");
        response.verifySchema ("create-booking-schema.json");
        response.verifyTextField ("bookingid")
            .isNotNull ();
        response.verifyTextField ("booking.firstname")
            .isEqualTo (this.newBooking.getFirstname ());
        response.verifyBooleanField ("booking.depositpaid")
            .isTrue ();
        response.verifyHeader ("Content-Type")
            .isEqualTo ("application/json; charset=utf-8");

        final var bookingId = response.getResponseData ("bookingid");
        getSession ().setSharedData (BOOKING_ID, bookingId);
    }

    @Test (description = "Test for Deleting a booking using DELETE request")
    public void testDeleteBooking () {
        final var request = deleteBooking (getSession ().getSharedData (BOOKING_ID));
        final var response = withRequest (request).execute ();
        
        response.verifyStatusCode ()
            .isEqualTo (201);
    }

    @Test (description = "Test for checking deleted booking using GET request")
    public void testDeletedBooking () {
        final var request = getBooking (getSession ().getSharedData (BOOKING_ID));
        final var response = withRequest (request).execute ();

        response.verifyStatusCode ()
            .isEqualTo (404);
    }

    @Test (description = "Test for retrieving booking using GET request")
    public void testGetBooking () {
        final var request = getBooking (getSession ().getSharedData (BOOKING_ID));
        final var response = withRequest (request).execute ();

        response.verifyStatusCode ()
            .isEqualTo (200);
        response.verifyTextField ("firstname")
            .isEqualTo (this.newBooking.getFirstname ());
        response.verifyTextField ("lastname")
            .isEqualTo (this.newBooking.getLastname ());
    }

    @Test (description = "Tests for file not found exception", expectedExceptions = FrameworkError.class)
    public void testJsonSchemaFileException () {
        final var request = createBooking (this.newBooking);
        final var response = withRequest (request).execute ();

        response.verifyStatusCode ()
            .isEqualTo (200);
        response.verifyStatusMessage ()
            .isEqualTo ("OK");
        response.verifySchema ("create-booking-scheme.json");
    }

    @Test (description = "Test for Updating booking using PUT request")
    public void testUpdateBooking () {
        final var updateBookingData = this.newBooking;

        final var request = BookingRequest.updateBooking (getSession ().getSharedData (BOOKING_ID), updateBookingData);
        final var response = withRequest (request).execute ();

        response.verifyStatusCode ()
            .isEqualTo (200);
        response.verifyTextField ("firstname")
            .isEqualTo (updateBookingData.getFirstname ());
        response.verifyTextField ("lastname")
            .isEqualTo (updateBookingData.getLastname ());
    }

    @Test (description = "Test for partial updating booking using PATCH request")
    public void testUpdatePartialBooking () {
        final var partialBookingData = BookingRequestData.getPartialBookingData ();

        final var request = BookingRequest.updatePartialBooking (getSession ().getSharedData (BOOKING_ID),
            partialBookingData);
        final var response = withRequest (request).execute ();

        response.verifyStatusCode ()
            .isEqualTo (200);
        response.verifyTextField ("firstname")
            .isEqualTo (partialBookingData.getFirstname ());
        response.verifyIntField ("totalprice")
            .isEqualTo (partialBookingData.getTotalprice ());
    }
}
