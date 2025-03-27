package com.boyka.demo.ui.sauce.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static io.appium.java_client.AppiumBy.iOSNsPredicateString;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import io.github.boykaframework.builders.Locator;
import lombok.Getter;

@Getter
public class CheckoutSuccessPage {
    private static final CheckoutSuccessPage SUCCESS_PAGE = new CheckoutSuccessPage ();

    public static CheckoutSuccessPage successPage () {
        return SUCCESS_PAGE;
    }

    private final Locator backHome       = Locator.buildLocator ()
        .name ("Back To Home")
        .android (accessibilityId ("test-BACK HOME"))
        .ios (accessibilityId ("test-BACK HOME"))
        .web (id ("back-to-products"))
        .build ();
    private final Locator successHeader  = Locator.buildLocator ()
        .name ("Success Header")
        .android (androidUIAutomator ("new UiSelector().className(\"android.widget.TextView\").instance(1)"))
        .ios (iOSNsPredicateString ("name ENDSWITH \"YOU ORDER\""))
        .web (cssSelector ("h2[data-test=\"complete-header\"]"))
        .build ();
    private final Locator successMessage = Locator.buildLocator ()
        .name ("Success Message")
        .android (androidUIAutomator ("new UiSelector().className(\"android.widget.TextView\").instance(2)"))
        .ios (iOSNsPredicateString ("name BEGINSWITH \"Your order\""))
        .web (cssSelector ("div[data-test=\"complete-text\"]"))
        .build ();

    private CheckoutSuccessPage () {
        // Singleton class.
    }
}
