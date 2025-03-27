package com.boyka.demo.ui.sauce;

import static com.boyka.demo.ui.sauce.actions.HomePageActions.verifyProductPurchase;
import static com.boyka.demo.ui.sauce.actions.LoginActions.verifyLogin;
import static com.boyka.demo.ui.sauce.actions.LoginActions.verifyLogout;
import static io.github.boykaframework.actions.drivers.WindowActions.onWindow;
import static io.github.boykaframework.manager.ParallelSession.clearSession;
import static io.github.boykaframework.manager.ParallelSession.createSession;

import io.github.boykaframework.enums.PlatformType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SauceDemoTest {
    @AfterMethod
    public void afterMethod (final ITestResult result) {
        if (!result.isSuccess ()) {
            onWindow ().takeScreenshot ();
        }
    }

    @Parameters ({ "platformType", "configKey" })
    @BeforeTest (description = "Setup test class")
    public void setupTest (final PlatformType platformType, final String configKey) {
        createSession (platformType, configKey);
    }

    @AfterTest (description = "Tear down test class")
    public void tearDownTest () {
        clearSession ();
    }

    @Test (description = "Test Checkout Product", dependsOnMethods = "testLogin")
    public void testCheckoutProduct () {
        verifyProductPurchase ("Sauce Labs Bike Light");
    }

    @Test (description = "Test Login Flow")
    public void testLogin () {
        verifyLogin ("standard_user", "secret_sauce", true);
    }

    @Test (description = "Test Logout", dependsOnMethods = "testCheckoutProduct")
    public void testLogout () {
        verifyLogout ();
    }
}
