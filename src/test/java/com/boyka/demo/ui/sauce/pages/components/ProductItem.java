package com.boyka.demo.ui.sauce.pages.components;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.By.cssSelector;

import io.github.boykaframework.builders.Locator;
import lombok.Builder;

@Builder (toBuilder = true, builderMethodName = "createItem", buildMethodName = "create")
public class ProductItem {
    private int     index;
    private String  name;
    private Locator parent;

    public Locator getAddToCart () {
        return Locator.buildLocator ()
            .parent (getContainer ())
            .name ("Add to Cart Button")
            .android (accessibilityId ("test-ADD TO CART"))
            .ios (accessibilityId ("test-ADD TO CART"))
            .web (cssSelector ("button[data-test^=\"add-to-cart-\"]"))
            .build ();
    }

    public Locator getDragHandle () {
        return Locator.buildLocator ()
            .name ("Product Drag Handle")
            .parent (getContainer ())
            .android (accessibilityId ("test-Drag Handle"))
            .ios (accessibilityId ("test-Drag Handle"))
            .build ();
    }

    public Locator getPrice () {
        return Locator.buildLocator ()
            .parent (getContainer ())
            .name ("Product Item Price")
            .android (accessibilityId ("test-Price"))
            .ios (accessibilityId ("test-Price"))
            .web (cssSelector ("div[data-test=\"inventory-item-price\"]"))
            .build ();
    }

    public Locator getTitles () {
        return Locator.buildLocator ()
            .name ("Product Item Name")
            .parent (this.parent)
            .android (accessibilityId ("test-Item title"))
            .ios (accessibilityId ("test-Item title"))
            .web (cssSelector ("div[data-test=\"inventory-item-name\"]"))
            .build ();
    }

    private Locator getContainer () {
        return Locator.buildLocator ()
            .name (this.name)
            .parent (this.parent)
            .index (this.index)
            .android (accessibilityId ("test-Item"))
            .ios (accessibilityId ("test-Item"))
            .web (cssSelector ("div[data-test=\"inventory-item\"]"))
            .build ();
    }
}
