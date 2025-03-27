package com.boyka.demo.ui.sauce.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static io.appium.java_client.AppiumBy.iOSClassChain;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import io.github.boykaframework.builders.Locator;
import lombok.Getter;

@Getter
public class CheckoutPage {
    private static final CheckoutPage CHECKOUT_PAGE = new CheckoutPage ();

    public static CheckoutPage checkoutPage () {
        return CHECKOUT_PAGE;
    }

    private final Locator cartPrice      = Locator.buildLocator ()
        .name ("Cart Price")
        .android (androidUIAutomator (
            "new UiSelector().description(\"test-Price\").childSelector(new UiSelector().className(\"android.widget.TextView\"))"))
        .ios (iOSClassChain ("**/XCUIElementTypeOther[`name == \"test-Price\"`]/XCUIElementTypeStaticText"))
        .web (cssSelector ("div[data-test=\"inventory-item-price\"]"))
        .build ();
    private final Locator checkoutButton = Locator.buildLocator ()
        .name ("Checkout Button")
        .android (accessibilityId ("test-CHECKOUT"))
        .ios (accessibilityId ("test-CHECKOUT"))
        .web (id ("checkout"))
        .build ();

    private CheckoutPage () {
        // Singleton page
    }
}
