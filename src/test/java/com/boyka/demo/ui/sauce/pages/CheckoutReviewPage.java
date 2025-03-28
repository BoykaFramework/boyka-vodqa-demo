package com.boyka.demo.ui.sauce.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static io.appium.java_client.AppiumBy.iOSNsPredicateString;
import static java.text.MessageFormat.format;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import io.github.boykaframework.builders.Locator;
import lombok.Getter;

@Getter
public class CheckoutReviewPage {
    private static final CheckoutReviewPage REVIEW_PAGE = new CheckoutReviewPage ();

    public static CheckoutReviewPage checkoutReviewPage () {
        return REVIEW_PAGE;
    }

    private final Locator finishButton = Locator.buildLocator ()
        .name ("Finish Button")
        .android (accessibilityId ("test-FINISH"))
        .ios (accessibilityId ("test-FINISH"))
        .web (id ("finish"))
        .build ();

    private CheckoutReviewPage () {
        // Singleton class.
    }

    public Locator getTotalPrice (final String price) {
        return Locator.buildLocator ()
            .name ("Total price")
            .android (androidUIAutomator (format ("new UiSelector().text(\"Total: {0}\")", price)))
            .ios (iOSNsPredicateString (format ("name == \"Total: {0}\"", price)))
            .web (cssSelector ("div[data-test=\"total-label\"]"))
            .build ();
    }
}
