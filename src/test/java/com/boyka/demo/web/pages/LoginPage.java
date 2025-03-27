package com.boyka.demo.web.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import io.github.boykaframework.builders.Locator;
import lombok.Getter;

@Getter
public class LoginPage {
    private static final LoginPage LOGIN_PAGE = new LoginPage ();

    public static LoginPage loginPage () {
        return LOGIN_PAGE;
    }

    private final Locator login    = Locator.buildLocator ()
        .name ("Login Button")
        .web (id ("login-button"))
        .android (accessibilityId ("test-LOGIN"))
        .ios (accessibilityId ("test-LOGIN"))
        .build ();
    private final Locator message  = Locator.buildLocator ()
        .name ("Message Label")
        .web (cssSelector ("h3[data-test=\"error\"]"))
        .android (accessibilityId ("test-Error message"))
        .ios (accessibilityId ("test-Error message"))
        .build ();
    private final Locator password = Locator.buildLocator ()
        .name ("Password")
        .web (id ("password"))
        .android (accessibilityId ("test-Password"))
        .ios (accessibilityId ("test-Password"))
        .build ();
    private final Locator userName = Locator.buildLocator ()
        .name ("User Name")
        .web (id ("user-name"))
        .android (accessibilityId ("test-Username"))
        .ios (accessibilityId ("test-Username"))
        .build ();

    private LoginPage () {
        // Utility class.
    }
}
