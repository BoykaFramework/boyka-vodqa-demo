package com.boyka.demo.web.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.By.id;

import io.github.boykaframework.builders.Locator;
import lombok.Getter;

@Getter
public class CheckoutInfoPage {
    private static final CheckoutInfoPage INFO_PAGE = new CheckoutInfoPage ();

    public static CheckoutInfoPage checkoutInfoPage () {
        return INFO_PAGE;
    }

    private final Locator continueButton = Locator.buildLocator ()
        .name ("Continue Button")
        .web (id ("continue"))
        .android (accessibilityId ("test-CONTINUE"))
        .ios (accessibilityId ("test-CONTINUE"))
        .build ();
    private final Locator firstName      = Locator.buildLocator ()
        .name ("First Name")
        .web (id ("first-name"))
        .android (accessibilityId ("test-First Name"))
        .ios (accessibilityId ("test-First Name"))
        .build ();
    private final Locator lastName       = Locator.buildLocator ()
        .name ("Last Name")
        .web (id ("last-name"))
        .android (accessibilityId ("test-Last Name"))
        .ios (accessibilityId ("test-Last Name"))
        .build ();
    private final Locator postalCode     = Locator.buildLocator ()
        .name ("Postal Code")
        .android (accessibilityId ("test-Zip/Postal Code"))
        .ios (accessibilityId ("test-Zip/Postal Code"))
        .web (id ("postal-code"))
        .build ();

    private CheckoutInfoPage () {
        // Singleton page.
    }
}
