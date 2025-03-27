package com.boyka.demo.web.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.androidUIAutomator;
import static io.appium.java_client.AppiumBy.iOSClassChain;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.linkText;

import com.boyka.demo.web.pages.components.ProductItem;
import io.github.boykaframework.builders.Locator;
import lombok.Getter;

@Getter
public class HomePage {
    private static final HomePage HOME = new HomePage ();

    public static HomePage homePage () {
        return HOME;
    }

    private final Locator     cart                  = Locator.buildLocator ()
        .name ("Cart")
        .android (accessibilityId ("test-Cart"))
        .ios (accessibilityId ("test-Cart"))
        .web (cssSelector ("a[data-test=\"shopping-cart-link\"]"))
        .build ();
    private final Locator     cartCount             = Locator.buildLocator ()
        .name ("Cart Item count")
        .android (androidUIAutomator (
            "new UiSelector().description(\"test-Cart\").childSelector(new UiSelector().className(\"android.widget.TextView\"))"))
        .ios (iOSClassChain ("**/XCUIElementTypeOther[`name == \"test-Cart\"`]/XCUIElementTypeOther"))
        .web (cssSelector ("span[data-test=\"shopping-cart-badge\"]"))
        .build ();
    private final Locator     dropZone              = Locator.buildLocator ()
        .name ("Product Drop Zone")
        .android (accessibilityId ("test-Cart drop zone"))
        .ios (accessibilityId ("test-Cart drop zone"))
        .build ();
    private final Locator     logout                = Locator.buildLocator ()
        .name ("Logout")
        .android (accessibilityId ("test-LOGOUT"))
        .ios (accessibilityId ("test-LOGOUT"))
        .web (linkText ("Logout"))
        .build ();
    private final Locator     menu                  = Locator.buildLocator ()
        .name ("Menu Button")
        .web (id ("react-burger-menu-btn"))
        .android (accessibilityId ("test-Menu"))
        .ios (accessibilityId ("test-Menu"))
        .build ();
    private final Locator     productItemsContainer = Locator.buildLocator ()
        .name ("Product Container")
        .android (accessibilityId ("test-PRODUCTS"))
        .ios (accessibilityId ("test-PRODUCTS"))
        .web (cssSelector ("div[data-test=\"inventory-list\"]"))
        .build ();
    private final ProductItem productItems          = ProductItem.createItem ()
        .name ("Product Items")
        .parent (this.productItemsContainer)
        .create ();

    private HomePage () {
        // Singleton class.
    }
}
