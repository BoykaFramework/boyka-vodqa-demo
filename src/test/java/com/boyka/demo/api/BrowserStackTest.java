package com.boyka.demo.api;

import static com.boyka.demo.api.requests.BrowserStackRequest.recentAppsRequest;
import static com.boyka.demo.api.requests.BrowserStackRequest.uploadApp;
import static io.github.boykaframework.actions.api.ApiActions.withRequest;
import static io.github.boykaframework.enums.PlatformType.API;
import static io.github.boykaframework.manager.ParallelSession.clearSession;
import static io.github.boykaframework.manager.ParallelSession.createSession;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BrowserStackTest {
    @BeforeTest
    public void setupTest () {
        createSession (API, "test_browser_stack");
    }

    @AfterTest
    public void tearDownTest () {
        clearSession ();
    }

    @Test
    public void testAppUpload () {
        final var request = uploadApp ();
        final var response = withRequest (request).execute ();

        response.verifyStatusCode ()
            .isEqualTo (200);
    }

    @Test
    public void testRecentApps () {
        final var request = recentAppsRequest ();
        final var response = withRequest (request).execute ();

        response.verifyStatusCode ()
            .isEqualTo (200);
        response.verifyIntField ("size()")
            .isGreaterThan (0);
    }
}
